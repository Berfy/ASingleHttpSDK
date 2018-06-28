package cn.berfy.sdk.http.model;

import java.util.LinkedHashMap;

public class HttpParams {

    private LinkedHashMap<String, Object> mHeaders = new LinkedHashMap<String, Object>();
    private LinkedHashMap<String, Object> mParams = new LinkedHashMap<String, Object>();
    private POST_TYPE mPostContentType;

    public HttpParams putParam(String key, Object value) {
        // TODO Auto-generated method stub
        if (!mParams.containsKey(key)) {
            mParams.put(key, value);
        }
        return this;
    }

    public LinkedHashMap<String, Object> getParams() {
        return mParams;
    }

    public void setParams(LinkedHashMap<String, Object> params) {
        mParams = params;
    }

    public enum POST_TYPE {
        POST_TYPE_FORM,//post表单请求
        POST_TYPE_JSON;//post body json
    }

    public HttpParams setContentType(POST_TYPE type) {
        mPostContentType = type;
        return this;
    }

    public POST_TYPE getContentType() {
        if (null == mPostContentType) {
            return POST_TYPE.POST_TYPE_FORM;
        }
        return mPostContentType;
    }

    public HttpParams putHeader(String key, Object value) {
        // TODO Auto-generated method stub
        if (!mHeaders.containsKey(key)) {
            mHeaders.put(key, value);
        }
        return this;
    }

    public LinkedHashMap<String, Object> getHeaders() {
        return mHeaders;
    }
}
