package cn.berfy.sdk.demohttp.rxjavademo

import android.content.Context

import cn.berfy.sdk.demohttp.rxjavademo.model.Book
import cn.berfy.sdk.http.HttpApi
import rx.Observable

/**
 * Created by win764-1 on 2016/12/12.
 */
class DemoHttpApi {
    val server: RetrofitService

    init {
        server = HttpApi.getInstances().getServer(RetrofitService::class.java)
    }

    companion object {
        private var mDemoHttpApi: DemoHttpApi? = null

        val instance: DemoHttpApi?
            @Synchronized get() {
                if (null == mDemoHttpApi) {
                    synchronized(DemoHttpApi::class.java) {
                        if (null == mDemoHttpApi) {
                            mDemoHttpApi = DemoHttpApi()
                        }
                    }
                }
                return mDemoHttpApi
            }
    }
}
