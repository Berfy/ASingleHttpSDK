package cn.berfy.sdk.http.callback;


import android.text.TextUtils;

import cn.berfy.sdk.http.config.Constant;
import cn.berfy.sdk.http.config.ServerStatusCodes;
import cn.berfy.sdk.http.http.okgo.model.Response;
import cn.berfy.sdk.http.http.okhttp.utils.GsonUtil;
import cn.berfy.sdk.http.http.okhttp.utils.HLogF;
import cn.berfy.sdk.http.model.NetError;
import cn.berfy.sdk.http.model.NetMessage;
import cn.berfy.sdk.http.model.NetResponse;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Berfy on 2017/12/15.
 * http接口封装回调处理类
 */

public class SuperOkHttpCallBack<T> {

    RequestCallBack<T> mRequestCallBack;

    public SuperOkHttpCallBack(RequestCallBack<T> requestCallBack) {
        mRequestCallBack = requestCallBack;
    }

    public void start() {
        if (null != mRequestCallBack)
            mRequestCallBack.onStart();
    }

    public void finishV1(int statusCode, String response, boolean needGson) {
        if (!needGson) {
            NetResponse<T> netResponse = new NetResponse<T>();
            netResponse.statusCode = statusCode;
            netResponse.data = (T) response;
            if (null != mRequestCallBack) {
                mRequestCallBack.onFinish(netResponse);
            }
        } else {
            NetResponse<T> netResponse = new NetResponse<T>();
            netResponse.statusCode = statusCode;
            try {
                JSONObject json = new JSONObject(response);
                NetMessage netMessage = new NetMessage();
                netMessage.code = json.optInt("code");
                netMessage.msg = json.optString("msg");
                netMessage.err_msg = json.optString("err_msg");
                try {
                    String data = json.optString("data");
                    T t = getContent(data);
                    if (null == t) {
                        try {
                            Type type = ((ParameterizedType) getClass()
                                    .getGenericSuperclass()).getActualTypeArguments()[0];
                            netResponse.data = GsonUtil.getInstance().toClass(response, type);
                        } catch (Exception e) {
                            netResponse.data = (T) data;
                        }
                    } else {
                        netResponse.data = t;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    HLogF.d(Constant.HTTPTAG, "data数据解析失败：\n" + e.getMessage() + "\n" + e.getCause());
                }
                if (null != mRequestCallBack)
                    mRequestCallBack.onFinish(netResponse);
            } catch (Exception e) {
                NetError netError = new NetError();
                netError.statusCode = statusCode;
                netError.errMsg = "数据格式错误";
                if (null != mRequestCallBack) {
                    mRequestCallBack.onError(netError);
                }
                e.printStackTrace();
                HLogF.d(Constant.HTTPTAG, "数据解析失败：\n" + e.getMessage() + "\n" + e.getCause());
            }
        }
    }

    public void finish(Response<String> response) {
        NetResponse<T> netResponse = new NetResponse<>();
        netResponse.statusCode = response.code();
        try {
            JSONObject json = new JSONObject(response.body());
            NetMessage netMessage = new NetMessage();
            netMessage.code = json.optInt("ret");
            netMessage.msg = json.optString("msg");
            try {
                try {
                    Type type = ((ParameterizedType) getClass()
                            .getGenericSuperclass()).getActualTypeArguments()[0];
                    netResponse.data = GsonUtil.getInstance().toClass(response.body(), type);
                } catch (Exception e) {
                    netResponse.data = (T) response.body();
                }
            } catch (Exception e) {
                e.printStackTrace();
                HLogF.d(Constant.HTTPTAG, "data数据解析失败：\n" + e.getMessage() + "\n" + e.getCause());
            }
            if (null != mRequestCallBack)
                mRequestCallBack.onFinish(netResponse);
        } catch (Exception e) {
            NetError netError = new NetError();
            netError.statusCode = ServerStatusCodes.RET_CODE_SYSTEM_ERROR;
            netError.errMsg = "数据格式错误";
            if (null != mRequestCallBack) {
                mRequestCallBack.onError(netError);
            }
            e.printStackTrace();
            HLogF.d(Constant.HTTPTAG, "数据解析失败：\n" + e.getMessage() + "\n" + e.getCause());
        }
    }

    protected T getContent(String json) {
        return null;
    }

    public void error(int statusCode, String errMsg) {
        NetError netError = new NetError();
        netError.statusCode = statusCode;
        netError.errMsg = errMsg;
        if (null != mRequestCallBack)
            mRequestCallBack.onError(netError);
    }
}
