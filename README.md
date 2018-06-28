HttpSDK说明文档
===============
#####支持
 - android4.4以上手机
 - Jdk 1.8
 - Android Studio 3.1.3版本

#####导入
 - 项目引用http-release.aar
     
      
      
          api(name:'http-release', ext:'aar')
          sfssfsf
    
 - 初始化
    
        - Applicaiton调用
        {
            HttpApi.init(context)
            HttpApi.getInstances()
                            .setHost("http://www.demo.com/")//请求域名
                            .setTimeOut(20, 20, 50)//连接 读取 写入 超市时间 单位 ms
                            .setCacheDir(localPath)//缓存目录
                            .setHeader(new HttpParams())//headers
                            .setStatusListener(new OnStatusListener() {
                                @Override
                                public void statusCodeError(int i) {//只有不是200-300的全局监听的错误状态码
                                    Log.d("httpLog", "测试  请求错误码" + i);
                                    mTvHttp.setText("请求错误码" + i);
                                }
            
                                @Override
                                public HttpParams addParams(HttpParams rawParams) {//处理当前参数和headers，返回的参数是在当前参数下增加，不是覆盖
                                    Log.d(HttpApi.getInstances().getLogTAG(), "测试  请求参数  " + GsonUtil.getInstance().toJson(rawParams.getParams()));
                                    Log.d(HttpApi.getInstances().getLogTAG(), "测试  请求Headers  " + GsonUtil.getInstance().toJson(rawParams.getHeaders()));
                                    rawParams.putParam("ts", System.currentTimeMillis() + "");
                                    Iterator<Map.Entry<String, Object>> headers = rawParams.getParams().entrySet().iterator();
                                    StringBuffer sb = new StringBuffer();
                                    while (headers.hasNext()) {
                                        Map.Entry<String, Object> entry = headers.next();
                                        sb.append(entry.getKey().trim()).append("=").append(entry.getValue().toString());
                                    }
            
                                    HttpParams httpParams1 = new HttpParams();
                                    httpParams1.putParam("ts", rawParams.getParams().get("ts"));
                                    httpParams1.putHeader("sign", HttpApi.getMd5(false, HttpApi.getInstances().encode3Des(sb.toString())));
                                    return httpParams1;
                                }
            
                                @Override
                                public void receiveSetCookie(String s) {//Set-Cookies
            
                                }
            
                                @Override
                                public HttpParams addCookies() {//单独添加全局的Headers
                                    return null;
                                }
                            })
                            .setLogTAG("httpLog")//log的TAG
                            .finish();//配置完成
        }
        
 - 用法
 
            - POST
              HttpParams httpParams = new HttpParams();
              //post的两种方式  get不需要设置
              httpParams.setContentType(HttpParams.POST_TYPE.POST_TYPE_JSON);//支持POST_TYPE_FORM form
              //表单和POST_TYPE_JSON json请求
              httpParams.putParam("a", 1);//参数
              httpParams.putHeader("hearfer", 1);//headers      
              HttpApi.getInstances().get ... 
              HttpApi.getInstances().post("http://192.168.1.27:18610/", "", httpParams, new HttpCallBack() {
              
                  @Override
                  public void onStart() {
                      Log.d("请求开始", "===");
                  }
              
                  @Override
                  public void onFinish(NetResponse<String> netResponse) {
                      Log.d("返回成功", "statusCode = " + netResponse.statusCode 
                          + " 返回值" + netResponse.data);
                      mTvHttp.setText(netResponse.data);
                  }
              
                  @Override
                  public void onError(int statusCode, String s) {
                      Log.d("返回错误", "statusCode = " + statusCode + " 错误信息" + s);
                      mTvHttp.setText(s);
                  }
              });
                              
            - 上传文件
              postFile(String url, String localPath, HttpCallBack callback)
              postFile(String host, String url, String localPath, HttpCallBack callback)            
                 
            - 下载文件
              //url下载地址  callBack回调 默认下载路径setCacheDir(localPath)下的file目录
              downFile(String url, DownloadFileCallBack callBack)
              //url下载地址  callBack回调 localPath下载路径
              downFile(String url, String localPath, DownloadFileCallBack callBack)
              //接口方法
              public interface DownloadFileCallBack {
            
                  void onStart(String url);
            
                  void onSuccess(String url, String localPath);
            
                  void onError(String url, String e);
              }
 