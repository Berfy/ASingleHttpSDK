package cn.berfy.sdk.demohttp

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView

/**
 * Created by Berfy on 2017/9/25.
 * 自定义的优化的WebView
 */

class AWebView : WebView {

    private val TAG = "AWebView"

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        settings.javaScriptEnabled = true//支持JS
        settings.javaScriptCanOpenWindowsAutomatically = true//设置允许JS打开新窗口
        settings.allowFileAccess = true// 设置允许访问文件数据
        settings.setSupportZoom(true)//启用缩放
        settings.builtInZoomControls = true//启动缩放工具
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.savePassword = false//禁止存储密码
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        settings.setAppCacheMaxSize((1024 * 1024 * 8).toLong())
        settings.setAppCacheEnabled(true)
        settings.setGeolocationEnabled(true)
        settings.userAgentString
        val ua = settings.userAgentString
        //        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.cacheMode = WebSettings.LOAD_NO_CACHE//设置不缓存
        clearCache(true)
        setAcceptThirdPartyCookies()
        versionCheck()
    }

    /**
     * 版本适配
     */
    private fun versionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    /**
     * 设置跨域cookie读取
     */
    private fun setAcceptThirdPartyCookies() {
        //target 23 default false, so manual set true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        }
    }

    override fun loadUrl(url: String) {
        Log.i(TAG, "加载网址$url")
        super.loadUrl(url)
    }

    override fun destroy() {
        settings.builtInZoomControls = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // call requies API Level 11 ( Android 3.0 + )
            settings.displayZoomControls = false
        }
        visibility = View.GONE
        pauseTimers()
        val view = rootView as ViewGroup
        view.removeAllViews()
        super.destroy()
    }
}
