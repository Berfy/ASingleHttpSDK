package cn.berfy.sdk.demohttp.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.ImageView
import cn.berfy.sdk.http.http.okhttp.utils.HLogF

import java.lang.reflect.Field


/**
 * UI工具类
 */
object DisplayUtil {
    /**
     * 屏幕密度
     */
    private var mWindowManager: WindowManager? = null

    /**
     * 获取屏幕密度
     */
    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    /**
     * 根据手机的分辨率从dp转成为px
     */
    @Deprecated("")
    fun dip2px(dpValue: Float, context: Context): Int {
        return Math.round(dpValue * context.resources.displayMetrics.density)
    }

    /**
     * 根据手机的分辨率从dp转成为px
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        return Math.round(dpValue * context.resources.displayMetrics.density)
    }

    /**
     * 根据手机的分辨率从px转成为dp
     */
    @Deprecated("")
    fun px2dip(context: Context, pxValue: Float): Int {
        return Math.round(pxValue / context.resources.displayMetrics.density)
    }

    /**
     * 获取WindowManager
     */
    fun getWindowManager(context: Context): WindowManager? {
        if (mWindowManager == null) {
            mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        return mWindowManager
    }

    /**
     * 获取手机屏幕分辨率宽度（竖屏状态）
     */
    fun getDisplayWidth(context: Context): Int {
        val dm = DisplayMetrics()
        getWindowManager(context)?.defaultDisplay?.getMetrics(dm)
        return Math.min(dm.widthPixels, dm.heightPixels)
    }

    /**
     * 获取手机屏幕分辨率高度（竖屏状态）
     */
    fun getDisplayHeight(context: Context): Int {
        val dm = DisplayMetrics()
        getWindowManager(context)?.defaultDisplay?.getMetrics(dm)
        return Math.max(dm.widthPixels, dm.heightPixels)
    }

    /**
     * 获取实际屏幕宽度
     *
     * @param screenOritation 屏幕方向
     */
    fun getRealDisplayWidth(context: Context, screenOritation: Int): Int {
        val dm = DisplayMetrics()
        getWindowManager(context)?.defaultDisplay?.getMetrics(dm)
        return if (screenOritation == Configuration.ORIENTATION_LANDSCAPE) Math.max(dm.widthPixels, dm.heightPixels) else Math.min(dm.widthPixels, dm.heightPixels)
    }

    /**
     * 获取实际屏幕高度
     *
     * @param screenOritation 屏幕方向
     */
    fun getRealDisplayHeight(context: Context, screenOritation: Int): Int {
        val dm = DisplayMetrics()
        getWindowManager(context)?.defaultDisplay?.getMetrics(dm)
        return if (screenOritation == Configuration.ORIENTATION_LANDSCAPE) Math.min(dm.widthPixels, dm.heightPixels) else Math.max(dm.widthPixels, dm.heightPixels)
    }

    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val dm = DisplayMetrics()
        getWindowManager(context)?.defaultDisplay?.getMetrics(dm)
        return dm
    }

    /**
     * 获取手机屏幕密度
     */
    fun getDisplayDensity(context: Context): Float {
        val dm = DisplayMetrics()
        getWindowManager(context)?.defaultDisplay?.getMetrics(dm)
        return dm.density
    }

    /**
     * 获取手机字体缩放密度
     */
    fun getScaledDensity(context: Context): Float {
        val dm = DisplayMetrics()
        getWindowManager(context)?.defaultDisplay?.getMetrics(dm)
        return dm.scaledDensity
    }

    /**
     * 根据ImageView的宽度动态设置高度，并保证image的宽高比例，注：center_crop按比例缩放
     */
    fun adjustImageHeight(imageView: ImageView) {
        val vto = imageView.viewTreeObserver
        // 保证Imageview测量完成
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                imageView.viewTreeObserver.removeGlobalOnLayoutListener(this)

                // 获得ImageView中Image的真实宽高，
                val dw = imageView.drawable.bounds.width()
                val dh = imageView.drawable.bounds.height()
                HLogF.d("lxy", "drawable_X = $dw, drawable_Y = $dh")

                // 获得ImageView中Image的变换矩阵
                val m = imageView.imageMatrix
                val values = FloatArray(10)
                m.getValues(values)

                // Image在绘制过程中的变换矩阵，从中获得x和y方向的缩放系数
                val sx = values[0]
                val sy = values[4]
                HLogF.d("lxy", "scale_X = $sx, scale_Y = $sy")

                // 计算Image在屏幕上实际绘制的宽高
                val cw = (dw * sx).toInt()
                val ch = (dh * sy).toInt()
                HLogF.d("lxy", "caculate_W = $cw, caculate_H = $ch")

                val lp = imageView.layoutParams
                lp.height = ch
                imageView.layoutParams = lp
            }
        })
    }

    /**
     * @return 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        var c: Class<*>? = null

        var obj: Any? = null

        var field: Field? = null

        var x = 0
        var sbar = 0

        try {

            c = Class.forName("com.android.internal.R\$dimen")

            obj = c!!.newInstance()

            field = c.getField("status_bar_height")

            x = Integer.parseInt(field!!.get(obj).toString())

            sbar = context.resources.getDimensionPixelSize(x)

        } catch (e1: Exception) {

            e1.printStackTrace()

        }

        return sbar
    }

    /**
     * @return actionBar 高度
     */
    fun getActionBarHeight(context: Context): Int {
        // Calculate ActionBar height
        val tv = TypedValue()
        var actionBarHeight = 0
        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
        }
        return actionBarHeight
    }

    //动态调整控件的位置
    fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
        if (v.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = v.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }

    //获取虚拟按键的高度
    fun getNavigationBarHeight(context: Activity): Int {
        var result = 0
        if (hasNavBar(context)) {
            val res = context.resources
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    fun hasNavBar(context: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val display = context.windowManager.defaultDisplay
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            return realSize.y != size.y
        } else {
            val menu = ViewConfiguration.get(context).hasPermanentMenuKey()
            val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            return if (menu || back) {
                false
            } else {
                true
            }
        }
    }
}
