package cn.berfy.sdk.demohttp

import android.Manifest
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import cn.berfy.sdk.demohttp.model.BaseResponse

import com.axingxing.demohttp.R
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions

import cn.berfy.sdk.demohttp.model.Data
import cn.berfy.sdk.demohttp.model.HomeTabsBean
import cn.berfy.sdk.demohttp.rxjavademo.model.Book
import cn.berfy.sdk.demohttp.rxjavademo.presenter.DemoHttpPresenter
import cn.berfy.sdk.demohttp.rxjavademo.view.IDemoHttpView
import cn.berfy.sdk.demohttp.util.Base64
import cn.berfy.sdk.demohttp.util.DisplayUtil
import cn.berfy.sdk.demohttp.util.MD5
import cn.berfy.sdk.http.HttpApi
import cn.berfy.sdk.http.callback.HttpCallBack
import cn.berfy.sdk.http.callback.OnStatusListener
import cn.berfy.sdk.http.callback.RequestCallBack
import cn.berfy.sdk.http.callback.SuperOkHttpCallBack
import cn.berfy.sdk.http.http.okhttp.utils.GsonUtil
import cn.berfy.sdk.http.model.HttpParams
import cn.berfy.sdk.http.model.NetError
import cn.berfy.sdk.http.model.NetResponse
import cn.berfy.sdk.http.v1.HttpApiV1
import cn.berfy.sdk.mvpbase.base.CommonActivity
import cn.berfy.sdk.mvpbase.util.DeviceUtils
import cn.berfy.sdk.mvpbase.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CommonActivity<IDemoHttpView, DemoHttpPresenter>(), View.OnClickListener, IDemoHttpView {

    private var mWebView1: AWebView? = null
    private var mWebView2: AWebView? = null
    private var mEditMd5: EditText? = null
    private var mWaterWaveView: WaterWaveView? = null
    private var mBtnAnim: Button? = null
    private var mBtnMd5: Button? = null
    private var mBtnMd5Java: Button? = null
    private var mBtnMd5HmacJava: Button? = null
    private var mBtnMd5Base64: Button? = null
    private var mBtnMd5Base64Java: Button? = null
    private var mTvMd5: TextView? = null
    private var mBtnHttpGET: Button? = null
    private var mBtnHttpPOST: Button? = null
    private var mTvHttp: TextView? = null

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
//            mWebView1!!.loadUrl("https://jjzzz1.win/intr/64c2391c4d563ad2")
//            mWebView2!!.loadUrl("http://blog.sina.com.cn/s/blog_472b14140102xuw4.html")
            sendEmptyMessageDelayed(0, 10000)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        showTitleBar()
        XXPermissions.with(mContext)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_PHONE_STATE)
                .request(object : OnPermission {
                    override fun noPermission(denied: MutableList<String>?, quick: Boolean) {

                    }

                    override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                    }
                })
        titleBar.setLeftIcon(false)
        titleBar.setTitle(R.string.app_name)
        ToastUtil.init(applicationContext)
        HttpApi.init(applicationContext)
        val layoutParams = (findViewById<View>(R.id.waterWaveView) as WaterWaveView).layoutParams as RelativeLayout.LayoutParams
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT
        layoutParams.height = DisplayUtil.getDisplayHeight(this)
        mWaterWaveView = findViewById(R.id.waterWaveView)
        mWaterWaveView!!.layoutParams = layoutParams
        mWaterWaveView!!.startWave()
        mWebView1 = findViewById(R.id.webView1)
        mWebView2 = findViewById(R.id.webView2)
        mEditMd5 = findViewById(R.id.edit_md5)
        mTvMd5 = findViewById(R.id.tv_md5)
        mBtnMd5 = findViewById(R.id.btn_md5_c)
        mBtnMd5Java = findViewById(R.id.btn_md5_java)
        mBtnMd5HmacJava = findViewById(R.id.btn_md5_hmac)
        mBtnMd5Base64 = findViewById(R.id.btn_md5_base64)
        mBtnMd5Base64Java = findViewById(R.id.btn_md5_base64_java)
        mTvHttp = findViewById(R.id.tv_response)
        mBtnHttpGET = findViewById(R.id.btn_http_get)
        mBtnHttpPOST = findViewById(R.id.btn_http_post)
        mBtnAnim = findViewById(R.id.btn_anim)
        mBtnAnim!!.setOnClickListener(this)
        mBtnMd5!!.setOnClickListener(this)
        mBtnMd5Java!!.setOnClickListener(this)
        mBtnMd5HmacJava!!.setOnClickListener(this)
        mBtnMd5Base64!!.setOnClickListener(this)
        mBtnMd5Base64Java!!.setOnClickListener(this)
        mBtnHttpGET!!.setOnClickListener(this)
        mBtnHttpPOST!!.setOnClickListener(this)
        btn_http_rxjava_get!!.setOnClickListener(this)
        btn_http_rxjava_post!!.setOnClickListener(this)
        btn_okgo_get!!.setOnClickListener(this)
        btn_okgo_post!!.setOnClickListener(this)
        findViewById<View>(R.id.btn_3des).setOnClickListener(this)
        HttpApi.init(applicationContext)
        HttpApi.getInstances()
                .setHost("https://www.baidu.com/")
                .setOnStatusListener(object : OnStatusListener {
                    override fun statusCodeError(i: Int, errMsg: String) {
                        Log.d("httpLog", "测试  请求错误码$i")
                        mTvHttp!!.text = "请求错误码$i errMsg=$errMsg"
                    }

                    override fun addParams(rawParams: HttpParams): HttpParams? {
                        val addParams = HttpParams()
                        val ctx = applicationContext
                        addParams.putParam("deviceid", DeviceUtils.getDeviceId(ctx))
                        addParams.putParam("pid", "gloudphone")
                        addParams.putParam("version", "3.1.6")
                        addParams.putParam("ver", "3.1.6")
                        addParams.putParam("hwdeviceid", DeviceUtils.getDeviceId(ctx))
                        addParams.putParam("mode", Build.MODEL)
                        addParams.putParam("language", "zh")
                        return addParams
                    }

                    override fun receiveSetCookie(s: String) {

                    }

                    override fun addCookies(): HttpParams? {
                        return null
                    }
                })
                .setLogTAG("httpLog")
                .start()
        HttpApiV1.init(applicationContext)
        HttpApiV1.getInstances()
                .setHost("https://www.baidu.com/")
                .setStatusListener(object : OnStatusListener {
                    override fun statusCodeError(i: Int, errMsg: String) {
                        Log.d("httpLog", "测试  请求错误码$i")
                        mTvHttp!!.text = "请求错误码$i errMsg=$errMsg"
                    }

                    override fun addParams(rawParams: HttpParams): HttpParams? {
                        val addParams = HttpParams()
                        val ctx = applicationContext
                        addParams.putParam("deviceid", DeviceUtils.getDeviceId(ctx))
                        addParams.putParam("pid", "gloudphone")
                        addParams.putParam("version", "3.1.6")
                        addParams.putParam("ver", "3.1.6")
                        addParams.putParam("hwdeviceid", DeviceUtils.getDeviceId(ctx))
                        addParams.putParam("mode", Build.MODEL)
                        addParams.putParam("language", "zh")
                        return addParams
                    }

                    override fun receiveSetCookie(s: String) {

                    }

                    override fun addCookies(): HttpParams? {
                        return null
                    }
                })
                .setLogTAG("httpLog")
                .finish()

//        val headers = HttpHeaders()
//        headers.put("commonHeaderKey1", "commonHeaderValue1")    //header不支持中文，不允许有特殊字符
//        headers.put("commonHeaderKey2", "commonHeaderValue2")
        mWebView1!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError) {
                super.onReceivedSslError(view, handler, error)
                handler.proceed()  // 接受所有网站的证书
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            //是否在webview内加载页面
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.url.toString())
                } else {
                    view.loadUrl(request.toString())
                }
                return false
            }
        }
        mWebView2!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                super.onReceivedSslError(view, handler, error)
                handler.proceed()  // 接受所有网站的证书
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            //是否在webview内加载页面
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.url.toString())
                } else {
                    view.loadUrl(request.toString())
                }
                return false
            }
        }
        XXPermissions.with(this)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(object : OnPermission {
                    override fun hasPermission(granted: List<String>, isAll: Boolean) {
                        if (isAll) {

                        }
                    }

                    override fun noPermission(denied: List<String>, quick: Boolean) {

                    }
                })
//        mHandler.sendEmptyMessage(0)
    }

    override fun initPresenter(): DemoHttpPresenter {
        return DemoHttpPresenter()
    }

    override fun hiddenLoadingView(msg: String?) {

    }

    override fun showLoadingView(msg: String?) {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_md5_c -> {
                var time = System.currentTimeMillis()
                var md5 = mEditMd5!!.text.toString().trim { it <= ' ' }
                mTvMd5!!.text = HttpApiV1.getInstances().encodeMD5(true, md5) + "  耗时:" + (System.currentTimeMillis() - time) + "ms"
            }
            R.id.btn_md5_java -> {
                var time = System.currentTimeMillis()
                var md5 = mEditMd5!!.text.toString().trim { it <= ' ' }
                mTvMd5!!.text = MD5.getStringMD5(md5).toUpperCase() + "  耗时:" + (System.currentTimeMillis() - time) + "ms"
            }
            R.id.btn_md5_hmac -> {
                var time = System.currentTimeMillis()
                var md5 = mEditMd5!!.text.toString().trim { it <= ' ' }
                mTvMd5!!.text = HttpApi.getInstances().getHmac(md5).toUpperCase() + "  耗时:" + (System.currentTimeMillis() - time) + "ms"
            }
            R.id.btn_md5_base64 -> {
                var time = System.currentTimeMillis()
                var md5 = mEditMd5!!.text.toString().trim { it <= ' ' }
                mTvMd5!!.text = HttpApiV1.getInstances().encodeBase64(md5) + "  耗时:" + (System.currentTimeMillis() - time) + "ms"
            }
            R.id.btn_md5_base64_java -> {
                var time = System.currentTimeMillis()
                var md5 = mEditMd5!!.text.toString().trim { it <= ' ' }
                try {
                    mTvMd5!!.text = Base64.encode(md5.toByteArray(charset("UTF-8"))) + "  耗时:" + (System.currentTimeMillis() - time) + "ms"
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            R.id.btn_3des -> {
                var time = System.currentTimeMillis()
                var md5 = mEditMd5!!.text.toString().trim { it <= ' ' }
                mTvMd5!!.text = HttpApiV1.getInstances().encode3Des(md5) + "  耗时:" + (System.currentTimeMillis() - time) + "ms"
            }
            R.id.btn_http_get -> {
                var httpParams = HttpParams()
                httpParams.putParam("m", "AsherBanner")
                httpParams.putParam("a", "get_asher_banner_tabs")
                HttpApiV1.getInstances().get("https://b2.51ias.com/", "api.php", httpParams,
                        object : SuperOkHttpCallBack<HomeTabsBean>(
                                object : RequestCallBack<HomeTabsBean> {
                                    override fun onStart() {
                                    }

                                    override fun onFinish(response: NetResponse<HomeTabsBean>?) {
                                        mTvHttp!!.text = "${response!!.data.ret}:${response!!.data.msg}"
                                    }

                                    override fun onError(error: NetError?) {
                                    }
                                }) {}, true)
            }
            R.id.btn_http_post -> {
                var httpParams = HttpParams()
                httpParams.setContentType(HttpParams.POST_TYPE.POST_TYPE_JSON)
                httpParams.putParam("a", 1)
                httpParams.putParam("b", 2)
                httpParams.putHeader("hearfer", 1)
                HttpApi.getInstances().post("", httpParams, object : HttpCallBack {

                    override fun onStart() {
                        Log.d("请求开始", "===")
                    }

                    override fun onFinish(netResponse: NetResponse<String>) {
                        Log.d("返回成功", "statusCode = " + netResponse.statusCode + " 返回值" + netResponse.data)
                        mTvHttp!!.text = netResponse.data
                    }

                    override fun onError(netError: NetError) {
                        Log.d("返回错误", "statusCode = " + netError.statusCode + " 错误信息" + netError.errMsg)
                        mTvHttp!!.text = netError.errMsg
                    }
                })
            }
            R.id.btn_anim -> if (mWaterWaveView!!.isRunning) {
                mBtnAnim!!.text = "开始动画"
                mWaterWaveView!!.stop()
                mWaterWaveView!!.visibility = View.GONE
            } else {
                mBtnAnim!!.text = "关闭动画"
                mWaterWaveView!!.visibility = View.VISIBLE
                mWaterWaveView!!.startWave()
            }
            R.id.btn_http_rxjava_get -> presenter.checkUpdate("1.4.6", object : rx.Observer<Data> {
                override fun onCompleted() {}

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    ToastUtil.getInstances().showShort(e.message)
                }

                override fun onNext(book: Data?) {
                    if (null != book) {
                        ToastUtil.getInstances().showShort("获取成功" + GsonUtil.getInstance().toJson(book))
                        mTvHttp!!.text = GsonUtil.getInstance().toJson(book)
                    } else {
                        ToastUtil.getInstances().showShort("获取成功book=null")
                    }
                }
            })
            R.id.btn_http_rxjava_post -> presenter.getSearchBooks("1", "", 1, 10, object : rx.Observer<Book> {
                override fun onCompleted() {}

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    ToastUtil.getInstances().showShort(e.message)
                }

                override fun onNext(book: Book?) {
                    if (null != book) {
                        ToastUtil.getInstances().showShort("获取成功" + GsonUtil.getInstance().toJson(book))
                        mTvHttp!!.text = GsonUtil.getInstance().toJson(book)
                    } else {
                        ToastUtil.getInstances().showShort("获取成功book=null")
                    }
                }
            })
            R.id.btn_okgo_get -> {
                HttpApi.getInstances().get("https://b2.51ias.com/", "api.php")
                        .param("m", "AsherBanner")
                        .param("a", "get_asher_banner_tabs")
                        .callBack(object : SuperOkHttpCallBack<HomeTabsBean>(object : RequestCallBack<HomeTabsBean> {
                            override fun onStart() {

                            }

                            override fun onFinish(response: NetResponse<HomeTabsBean>?) {
                                mTvHttp!!.text = "${response!!.data.ret}:${response!!.data.msg}"
                            }

                            override fun onError(error: NetError?) {
                            }
                        }) {})
            }
            R.id.btn_okgo_post -> {
                HttpApi.getInstances().post("https://b2.51ias.com/api.php")
                        .param("m", "AsherBanner")
                        .param("a", "get_asher_banner_tabs")
                        .callBack(object : SuperOkHttpCallBack<HomeTabsBean>(object : RequestCallBack<HomeTabsBean> {
                            override fun onStart() {

                            }

                            override fun onFinish(response: NetResponse<HomeTabsBean>?) {
                                mTvHttp!!.text = GsonUtil.getInstance().toJson(response!!.data)
                            }

                            override fun onError(error: NetError?) {
                                mTvHttp!!.text = GsonUtil.getInstance().toJson(error)
                            }
                        }) {})
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mWaterWaveView!!.stop()
        mHandler.removeCallbacksAndMessages(null)
    }

    companion object {

        //加载so库
        init {
            System.loadLibrary("app-lib")
        }
    }
}
