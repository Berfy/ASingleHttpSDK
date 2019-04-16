package cn.berfy.sdk.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.berfy.sdk.http.callback.DownloadFileCallBack;
import cn.berfy.sdk.http.callback.HttpCallBack;
import cn.berfy.sdk.http.callback.OnStatusListener;
import cn.berfy.sdk.http.callback.SuperOkHttpCallBack;
import cn.berfy.sdk.http.config.Constant;
import cn.berfy.sdk.http.config.ServerStatusCodes;
import cn.berfy.sdk.http.http.interceptor.AddCookiesInterceptor;
import cn.berfy.sdk.http.http.interceptor.Md5SignInterceptor;
import cn.berfy.sdk.http.http.interceptor.ReceivedCookiesInterceptor;
import cn.berfy.sdk.http.http.interceptor.UserAgentInterceptor;
import cn.berfy.sdk.http.http.okgo.OkGo;
import cn.berfy.sdk.http.http.okgo.cache.CacheEntity;
import cn.berfy.sdk.http.http.okgo.cache.CacheMode;
import cn.berfy.sdk.http.http.okgo.callback.StringCallback;
import cn.berfy.sdk.http.http.okgo.model.HttpHeaders;
import cn.berfy.sdk.http.http.okgo.request.GetRequest;
import cn.berfy.sdk.http.http.okgo.request.PostRequest;
import cn.berfy.sdk.http.http.okhttp.OkHttpUtils;
import cn.berfy.sdk.http.http.okhttp.https.HttpsUtils;
import cn.berfy.sdk.http.http.okhttp.log.LoggerInterceptor;
import cn.berfy.sdk.http.http.okhttp.utils.FileUtils;
import cn.berfy.sdk.http.http.okhttp.utils.GsonUtil;
import cn.berfy.sdk.http.http.okhttp.utils.HLogF;
import cn.berfy.sdk.http.http.okhttp.utils.Hmac;
import cn.berfy.sdk.http.http.okhttp.utils.NetworkUtil;
import cn.berfy.sdk.http.model.HttpParams;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpApi {

    private Context mContext;
    public static HttpApi mHttpApi;
    private OnStatusListener mOnStatusListener;
    private String mBaseUrl = "";
    private long mConnectTimeoutSecond = 20;
    private long mReadTimeoutSecond = 20;
    private long mWriteTimeoutSecond = 60;
    private HttpParams mHeaders;
    private Retrofit mRetrofit = null;

    public static HttpApi init(Context context) {
        if (null == mHttpApi) {
            mHttpApi = new HttpApi(context);
        }
        return mHttpApi;
    }

    synchronized public static HttpApi getInstances() {
        if (null == mHttpApi) {
            synchronized (HttpApi.class) {
                if (null == mHttpApi) {
                    throw new NullPointerException("没有初始化HttpApi");
                }
            }
        }
        return mHttpApi;
    }

    private HttpApi(Context context) {
        mContext = context;
        HLogF.d(Constant.HTTPTAG, "接口服务初始化...");
    }

    public Context getContext() {
        return mContext;
    }

    public HttpApi setHost(String baseUrl) {
        HLogF.d(Constant.HTTPTAG, "新的主机名" + baseUrl);
        mBaseUrl = baseUrl;
        return mHttpApi;
    }

    public String getHost() {
        return mBaseUrl;
    }

    public HttpApi setTimeOut(long connectTimeoutSecond, long readTimeoutSecond, long writeTimeoutSecond) {
        mConnectTimeoutSecond = connectTimeoutSecond;
        mReadTimeoutSecond = readTimeoutSecond;
        mWriteTimeoutSecond = writeTimeoutSecond;
        return mHttpApi;
    }

//    public HttpApi setHeader(HttpParams headers) {
//        mHeaders = headers;
//        return mHttpApi;
//    }

    public HttpApi setCacheDir(String dir) {
        HLogF.d(Constant.HTTPTAG, "缓存目录" + dir);
        Constant.setCacheDir(dir);
        return mHttpApi;
    }

    public HttpApi setOnStatusListener(OnStatusListener onStatusListener) {
        mOnStatusListener = onStatusListener;
        return mHttpApi;
    }

    public HttpApi setDebug(boolean isDebug) {
        Constant.DEBUG = isDebug;
        return mHttpApi;
    }

    public HttpApi setLogTAG(String tag) {
        Constant.HTTPTAG = tag;
        return mHttpApi;
    }

    public String getLogTAG() {
        return Constant.HTTPTAG;
    }

    public void start() {
        HLogF.d(Constant.HTTPTAG, "接口服务初始化完毕");
        if (TextUtils.isEmpty(mBaseUrl)) {
            HLogF.d(Constant.HTTPTAG, "没有设置主机名");
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //设置拦截器，以用于自定义Cookies的设置
        builder.addInterceptor(new AddCookiesInterceptor(mContext, mOnStatusListener));
        builder.addInterceptor(new ReceivedCookiesInterceptor(mContext, Constant.HTTPTAG, mOnStatusListener));
        builder.addInterceptor(new Md5SignInterceptor(Constant.HTTPTAG, mOnStatusListener));//接口签名
        builder.addInterceptor(new LoggerInterceptor(Constant.HTTPTAG, true));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(mConnectTimeoutSecond, TimeUnit.SECONDS);
        builder.readTimeout(mReadTimeoutSecond, TimeUnit.SECONDS);
        builder.writeTimeout(mWriteTimeoutSecond, TimeUnit.SECONDS);
        builder.addInterceptor(new UserAgentInterceptor(mContext, HttpHeaders.getUserAgent(mContext), Constant.HTTPTAG));
        OkHttpClient okHttpClient = builder.build();
        OkHttpUtils.initClient(okHttpClient);
//        Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getHttpClient()));

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(OkHttpUtils.getInstance().getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        OkGo.getInstance()
                .init(mContext)
                .setOkHttpClient(okHttpClient)
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
    }

    public OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder1 = new OkHttpClient.Builder();
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory(null, null, null);
        builder1.addInterceptor(new LoggerInterceptor(Constant.HTTPTAG, true));
        builder1.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        return builder1.build();
    }

    public <T> T getServer(final Class<T> service) {
        return mRetrofit.create(service);
    }

    public SuperRequest get(String url) {
        return get("", url);
    }

    public SuperRequest get(String host, String url) {
        return new SuperRequest(SuperRequest.GET, host, url);
    }

    public SuperRequest post(String url) {
        return post("", url);
    }

    public SuperRequest post(String host, String url) {
        return new SuperRequest(SuperRequest.POST, host, url);
    }

    public SuperRequest postJson(String url) {
        return postJson("", url);
    }

    public SuperRequest postJson(String host, String url) {
        return new SuperRequest(SuperRequest.POST_JSON, host, url);
    }

    public static class SuperRequest {

        private String mHost;
        private String mUrl;
        private HttpParams mHttpParams;
        private int mRequestType;//0 get 1 post
        public static final int GET = 0;
        public static final int POST = 1;
        public static final int POST_JSON = 2;

        public SuperRequest(int requestType, String url) {
            mRequestType = requestType;
            mUrl = url;
        }

        public SuperRequest(int requestType, String host, String url) {
            mRequestType = requestType;
            mHost = host;
            mUrl = url;
        }

        public SuperRequest header(String key, String value) {
            if (null == mHttpParams) {
                mHttpParams = new HttpParams();
            }
            mHttpParams.putHeader(key, value);
            return this;
        }

        public SuperRequest headers(HttpHeaders httpHeaders) {
            if (null == mHttpParams) {
                mHttpParams = new HttpParams();
            }
            mHttpParams.putHeaders(httpHeaders);
            return this;
        }

        public SuperRequest param(String key, String value) {
            if (null == mHttpParams) {
                mHttpParams = new HttpParams();
            }
            mHttpParams.putParam(key, value);
            return this;
        }

        public <T> void callBack(SuperOkHttpCallBack<T> callback) {
            if (TextUtils.isEmpty(mHost)) {
                mHost = HttpApi.getInstances().getHost();
            }
            switch (mRequestType) {
                case GET:
                    HttpApi.getInstances().get(mHost, mUrl, mHttpParams, callback);
                    break;
                case POST:
                    HttpApi.getInstances().post(mHost, mUrl, mHttpParams, callback);
                    break;
                case POST_JSON:
                    HttpApi.getInstances().postJson(mHost, mUrl, mHttpParams, callback);
                    break;
            }
        }
    }

    public void get(String url, HttpParams httpParams, HttpCallBack callback) {
        SuperOkHttpCallBack<String> superOkHttpCallBack = new SuperOkHttpCallBack<String>(callback);
        get(mBaseUrl, url, httpParams, superOkHttpCallBack);
    }

    public void get(String host, String url, HttpParams httpParams, HttpCallBack callback) {
        SuperOkHttpCallBack<String> superOkHttpCallBack = new SuperOkHttpCallBack<String>(callback);
        get(host, url, httpParams, superOkHttpCallBack);
    }

    public <T> void get(String url, HttpParams httpParams, SuperOkHttpCallBack<T> callback) {
        get(mBaseUrl, url, httpParams, callback);
    }

    public <T> void get(String host, String url, HttpParams httpParams, final SuperOkHttpCallBack<T> callback) {
        HLogF.d(Constant.HTTPTAG, "get请求");
        if (TextUtils.isEmpty(host) && TextUtils.isEmpty(mBaseUrl)) {
            if (null != callback) {
                callback.error(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, "没有host");
            }
            return;
        }
        //host有/
        boolean isHasHostIndex = false;
        if (!TextUtils.isEmpty(host))
            if (host.substring(host.length() - 1, host.length()).equals("/")) {
                isHasHostIndex = true;
            }
        //去除多余/
        if (!TextUtils.isEmpty(url))
            if (isHasHostIndex && !TextUtils.isEmpty(url) && url.substring(0, 1).equals("/")) {
                url = url.substring(1, url.length());
            }
        if (null == httpParams) {
            httpParams = new HttpParams();
        }
        final String finalUrl = host + url;

        GetRequest<String> getRequest = OkGo.<String>get(finalUrl)
                .tag(this);
        //添加header
        Iterator<Map.Entry<String, Object>> headers = httpParams.getHeaders().entrySet().iterator();
        while (headers.hasNext()) {
            Map.Entry<String, Object> entry = headers.next();
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue().toString())) {
                getRequest.headers(entry.getKey().trim(), entry.getValue().toString());
            }
        }
        //添加params参数
        Iterator<Map.Entry<String, Object>> params = httpParams.getParams().entrySet().iterator();
        while (params.hasNext()) {
            Map.Entry<String, Object> entry = params.next();
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue().toString())) {
                getRequest.params(entry.getKey().trim(), entry.getValue().toString());
            }
        }
        if (null != callback) {
            callback.start();
        }

        getRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(cn.berfy.sdk.http.http.okgo.model.Response<String> response) {
                checkResultNew(response, callback);
            }

            @Override
            public void onError(cn.berfy.sdk.http.http.okgo.model.Response<String> response) {
                doError(finalUrl, response.getException(), callback);
            }
        });
    }

    //post请求
    public void post(String url, HttpParams httpParams, HttpCallBack callback) {
        SuperOkHttpCallBack<String> superOkHttpCallBack = new SuperOkHttpCallBack<String>(callback);
        switch (httpParams.getContentType()) {
            case POST_TYPE_FORM:
                post(mBaseUrl, url, httpParams, superOkHttpCallBack);
                break;
            case POST_TYPE_JSON:
                postJson(mBaseUrl, url, httpParams, superOkHttpCallBack);
                break;
        }
    }

    //post请求
    public void post(String host, String url, HttpParams httpParams, HttpCallBack callback) {
        SuperOkHttpCallBack<String> superOkHttpCallBack = new SuperOkHttpCallBack<String>(callback);
        switch (httpParams.getContentType()) {
            case POST_TYPE_FORM:
                post(host, url, httpParams, superOkHttpCallBack);
                break;
            case POST_TYPE_JSON:
                postJson(host, url, httpParams, superOkHttpCallBack);
                break;
            default:
                post(host, url, httpParams, superOkHttpCallBack);
                break;
        }
    }

    public <T> void post(String url, HttpParams httpParams, SuperOkHttpCallBack<T> callback) {
        switch (httpParams.getContentType()) {
            case POST_TYPE_FORM:
                post(mBaseUrl, url, httpParams, callback);
                break;
            case POST_TYPE_JSON:
                postJson(mBaseUrl, url, httpParams, callback);
                break;
        }
    }

    //form post请求（不开放）
    private <T> void post(String host, String url, HttpParams
            httpParams, final SuperOkHttpCallBack<T> callback) {
        HLogF.d(Constant.HTTPTAG, "post请求");
        if (TextUtils.isEmpty(host) && TextUtils.isEmpty(mBaseUrl)) {
            if (null != callback) {
                callback.error(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, "没有host");
            }
            return;
        }
        //host有/
        boolean isHasHostIndex = false;
        if (!TextUtils.isEmpty(host))
            if (host.substring(host.length() - 1, host.length()).equals("/")) {
                isHasHostIndex = true;
            }
        if (!TextUtils.isEmpty(url))
            if (isHasHostIndex && !TextUtils.isEmpty(url) && url.substring(0, 1).equals("/")) {
                url = url.substring(1, url.length());
            }
        if (null == httpParams) {
            httpParams = new HttpParams();
        }
        final String finalUrl = host + url;

        PostRequest<String> postRequest = OkGo.<String>post(finalUrl)
                .tag(this);
        //添加header
        Iterator<Map.Entry<String, Object>> headers = httpParams.getHeaders().entrySet().iterator();
        while (headers.hasNext()) {
            Map.Entry<String, Object> entry = headers.next();
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue().toString())) {
                postRequest.headers(entry.getKey().trim(), entry.getValue().toString());
            }
        }

        //添加form参数
        Iterator<Map.Entry<String, Object>> params = httpParams.getParams().entrySet().iterator();
        while (params.hasNext()) {
            Map.Entry<String, Object> entry = params.next();
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue().toString())) {
                postRequest.params(entry.getKey().trim(), entry.getValue().toString());
            }
        }
        if (null != callback) {
            callback.start();
        }

        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(cn.berfy.sdk.http.http.okgo.model.Response<String> response) {
                checkResultNew(response, callback);
            }

            @Override
            public void onError(cn.berfy.sdk.http.http.okgo.model.Response<String> response) {
                doError(finalUrl, response.getException(), callback);
            }
        });
    }

    public String getHmac(String text) {
        return Hmac.md5(text);
    }

    //body json post请求
    private <T> void postJson(String url, HttpParams
            httpParams, SuperOkHttpCallBack<T> callback, boolean needGson) {
        postJson(mBaseUrl, url, httpParams, callback);
    }

    //body json post请求
    private <T> void postJson(String host, String url, HttpParams
            httpParams, final SuperOkHttpCallBack<T> callback) {
        HLogF.d(Constant.HTTPTAG, "post请求(json)");
        if (TextUtils.isEmpty(host) && TextUtils.isEmpty(mBaseUrl)) {
            if (null != callback)
                callback.error(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, "没有host");
            return;
        }
        //host有/
        boolean isHasHostIndex = false;
        if (!TextUtils.isEmpty(host))
            if (host.substring(host.length() - 1, host.length()).equals("/")) {
                isHasHostIndex = true;
            }
        if (!TextUtils.isEmpty(url))
            if (isHasHostIndex && !TextUtils.isEmpty(url) && url.substring(0, 1).equals("/")) {
                url = url.substring(1, url.length());
            }
        if (null == httpParams) {
            httpParams = new HttpParams();
        }
        String json = "";
        final String finalUrl = host + url;
        //Hashmap序列化json参数
        try {
            json = GsonUtil.getInstance().toJson(httpParams.getParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != callback) {
            callback.start();
        }

        PostRequest<String> postRequest = OkGo.<String>post(finalUrl)
                .tag(this);
        postRequest.upJson(json);
        //添加header
        Iterator<Map.Entry<String, Object>> headers = httpParams.getHeaders().entrySet().iterator();
        while (headers.hasNext()) {
            Map.Entry<String, Object> entry = headers.next();
            if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue().toString())) {
                postRequest.headers(entry.getKey().trim(), entry.getValue().toString());
            }
        }
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(cn.berfy.sdk.http.http.okgo.model.Response<String> response) {
                checkResultNew(response, callback);
            }

            @Override
            public void onError(cn.berfy.sdk.http.http.okgo.model.Response<String> response) {
                doError(finalUrl, response.getException(), callback);
            }
        });
    }

    public <T> void postFile(String url, String localPath, SuperOkHttpCallBack<T> callback) {
        postFile(mBaseUrl, url, localPath, callback);
    }

    public <T> void postFile(String host, String url, String localPath, final SuperOkHttpCallBack<T> callback) {
        if (TextUtils.isEmpty(host) && TextUtils.isEmpty(mBaseUrl)) {
            if (null != callback)
                callback.error(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, "没有host");
            return;
        }
        //host有/
        boolean isHasHostIndex = false;
        if (!TextUtils.isEmpty(host))
            if (host.substring(host.length() - 1, host.length()).equals("/")) {
                isHasHostIndex = true;
            }
        if (!TextUtils.isEmpty(url))
            if (isHasHostIndex && !TextUtils.isEmpty(url) && url.substring(0, 1).equals("/")) {
                url = url.substring(1, url.length());
            }
        final String finalUrl = host + url;

        PostRequest<String> postRequest = OkGo.<String>post(finalUrl);
        postRequest.tag(localPath);
        postRequest.upFile(new File(localPath));
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(cn.berfy.sdk.http.http.okgo.model.Response<String> response) {
                checkResultNew(response, callback);
            }

            @Override
            public void onError(cn.berfy.sdk.http.http.okgo.model.Response<String> response) {
                doError(finalUrl, response.getException(), callback);
            }
        });
    }

    public void downFile(String url, DownloadFileCallBack callBack) {
        String localPath = Constant.FILE_CACHE + File.separator + Hmac.md5(url.getBytes());
        downFile(url, localPath, callBack);
    }

    public void downFile(final String url, final String localPath, final DownloadFileCallBack callBack) {
        final Request request = new Request.Builder().tag(System.currentTimeMillis()).url(url).build();
        final Call call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        if (FileUtils.exists(localPath)) {
            OkHttpUtils.getInstance().getPlatform().execute(new Runnable() {
                @Override
                public void run() {
                    if (null != callBack) {
                        HLogF.d(Constant.HTTPTAG, "保存成功 文件已经存在" + localPath);
                        callBack.onSuccess(url, localPath);
                    }
                }
            });
        } else {
            if (null != callBack) {
                callBack.onStart(url);
            }
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (null != callBack) {
                        callBack.onError(url, e.getMessage());
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    FileOutputStream fos = null;
                    InputStream is = null;
                    try {
                        long total = response.body().contentLength();
                        Log.e(Constant.HTTPTAG, "文件大小 total------>" + total);
                        final File file = new File(localPath);
                        byte[] buf = new byte[2048];
                        int len = 0;
                        long current = 0;
                        is = response.body().byteStream();
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            current += len;
                            fos.write(buf, 0, len);
                            Log.e(Constant.HTTPTAG, "文件大小 下载中------>" + total + ":" + current);
                        }
                        fos.flush();
                        OkHttpUtils.getInstance().getPlatform().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (null != callBack) {
                                    HLogF.d(Constant.HTTPTAG, "保存成功" + file.getAbsolutePath());
                                    callBack.onSuccess(url, file.getAbsolutePath());
                                }
                            }
                        });
                    } catch (final IOException e) {
                        HLogF.d(Constant.HTTPTAG, "保存失败" + e.getMessage());
                        OkHttpUtils.getInstance().getPlatform().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (null != callBack) {
                                    callBack.onError(url, e.getMessage());
                                }
                            }
                        });
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (final IOException e) {
                            HLogF.d(Constant.HTTPTAG, "保存失败" + e.getMessage());
                            OkHttpUtils.getInstance().getPlatform().execute(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != callBack) {
                                        callBack.onError(url, e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void doError(String url, Throwable e, SuperOkHttpCallBack callBack) {
        String msg = "";
        int statusCode = ServerStatusCodes.RET_CODE_SYSTEM_ERROR;
        if (!NetworkUtil.isNetAvailable(mContext)) {
            msg = "没有网络";
            statusCode = ServerStatusCodes.NO_NET;
            HLogF.d(Constant.HTTPTAG, "请求失败doError\n" + url + "\n 没有网络");

        } else {
            if (null != e && null != e.getMessage()) {
                if ((e.getMessage().toLowerCase().contains("unknowhost")) ||
                        (e.getMessage().toLowerCase().contains("sockettimeoutexception")) ||
                        (e.getMessage().toLowerCase().contains("timeout")) ||
                        (e.getMessage().toLowerCase().contains("after")
                                && e.getMessage().contains("ms")
                                && e.getMessage().startsWith("failed to connect to"))) {
                    msg = "请求超时";
                    statusCode = ServerStatusCodes.RET_CODE_TIMEOUT;
                } else {
                    msg = "数据格式不正确";
                    statusCode = ServerStatusCodes.RET_CODE_SYSTEM_ERROR;
                }
            } else {
                msg = "数据格式不正确 异常信息为空";
                statusCode = ServerStatusCodes.RET_CODE_SYSTEM_ERROR;
                msg += "\n" + e.getMessage() + "\n" + e.getCause();
            }
            HLogF.d(Constant.HTTPTAG, "请求失败doError\n" + url + "\n" + e.getMessage() + "\n" + e.getCause());
        }
        if (null != callBack) {
            callBack.error(statusCode, msg);
        }
        if (null != mOnStatusListener) {
            mOnStatusListener.statusCodeError(statusCode, msg);
        }
    }

    //拦截错误
    private <T> void checkResultNew(cn.berfy.sdk.http.http.okgo.model.Response<String>
                                            response, SuperOkHttpCallBack callBack) {
        if (!NetworkUtil.isNetAvailable(mContext)) {
            if (null != callBack) {
                callBack.error(ServerStatusCodes.NO_NET, "没有网络");
            }
            if (null != mOnStatusListener) {
                mOnStatusListener.statusCodeError(ServerStatusCodes.NO_NET, "没有网络");
            }
            HLogF.d(Constant.HTTPTAG, "请求失败checkResultNew\n 没有网络");
        } else {
            if (null == response) {
                HLogF.d(Constant.HTTPTAG, "请求错误response==null");
                if (null != callBack) {
                    callBack.error(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, "返回值为空");
                }
                if (null != mOnStatusListener) {
                    mOnStatusListener.statusCodeError(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, "返回值为空");
                }
                return;
            }
            if (null == response.body()) {
                HLogF.d(Constant.HTTPTAG, "请求错误null == response.body()");
                if (null != callBack) {
                    callBack.error(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, "返回值为空");
                }
                return;
            }
            //只单独拦截了404
            if (response.code() == 404) {
                HLogF.d(Constant.HTTPTAG, "请求错误404");
                if (null != callBack) {
                    callBack.error(response.code(), "请求错误404");
                }
                if (null != mOnStatusListener) {
                    mOnStatusListener.statusCodeError(404, "请求错误404");
                }
                return;
            }
            if (response.code() >= 200 && response.code() <= 300) {//状态码
                try {
                    if (null != callBack) {
                        HLogF.d(Constant.HTTPTAG, "解析正确" + response.body().getClass().getName());
                        callBack.finish(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    HLogF.d(Constant.HTTPTAG, "请求错误码解析出错" + e.getMessage());
                    if (null != callBack) {
                        callBack.error(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, e.getMessage());
                    }
                    if (null != mOnStatusListener) {
                        mOnStatusListener.statusCodeError(ServerStatusCodes.RET_CODE_SYSTEM_ERROR, "返回值解析出错");
                    }
                }
            } else {
                HLogF.d(Constant.HTTPTAG, "请求失败checkResultNew\n code=" + response.code() + "\n请求错误");
                if (null != mOnStatusListener) {
                    mOnStatusListener.statusCodeError(response.code(), "请求错误");
                }
            }
        }
    }
}
