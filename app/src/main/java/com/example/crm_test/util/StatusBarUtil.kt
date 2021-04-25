package com.example.crm_test.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.ColorInt

/**
 * Created by Jaeger on 16/2/14.
 *
 *
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
object StatusBarUtil {

    private const val TAG_KEY_HAVE_SET_OFFSET = -123

    /**
     * 设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColor(activity: Activity, @ColorInt color: Int = 0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = color
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorView = activity.window.decorView as ViewGroup
            val fakeStatusBarView = decorView.getTag(TAG_KEY_HAVE_SET_OFFSET) as? View
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(color)
            } else {
                val createStatusBarView = createStatusBarView(activity, color)
                decorView.addView(createStatusBarView)
                decorView.setTag(TAG_KEY_HAVE_SET_OFFSET, createStatusBarView)
            }
            setRootView(activity)
        }
    }



    @TargetApi(Build.VERSION_CODES.M)
    fun setLightMode(activity: Activity) {
        setMIUIStatusBarDarkIcon(activity, true)
        setMeizuStatusBarDarkIcon(activity, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun setDarkMode(activity: Activity) {
        setMIUIStatusBarDarkIcon(activity, false)
        setMeizuStatusBarDarkIcon(activity, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    /**
     * 修改 MIUI V6  以上状态栏颜色
     */
    private fun setMIUIStatusBarDarkIcon(
        activity: Activity,
        darkIcon: Boolean
    ) {
        val clazz: Class<out Window?> = activity.window.javaClass
        try {
            val layoutParams =
                Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field =
                layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod(
                "setExtraFlags",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            extraFlagField.invoke(
                activity.window,
                if (darkIcon) darkModeFlag else 0,
                darkModeFlag
            )
        } catch (e: Exception) {
            //e.printStackTrace();
        }
    }

    /**
     * 修改魅族状态栏字体颜色 Flyme 4.0
     */
    private fun setMeizuStatusBarDarkIcon(
        activity: Activity,
        darkIcon: Boolean
    ) {
        try {
            val lp = activity.window.attributes
            val darkFlag =
                WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags =
                WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (darkIcon) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            activity.window.attributes = lp
        } catch (e: Exception) {
            //e.printStackTrace()
        }
    }


    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(
        activity: Activity,
        @ColorInt color: Int = 0,
        alpha: Int = 0
    ): View {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.layoutParams = params
        if (color == 0) {
            statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        } else {
            statusBarView.setBackgroundColor(color)
        }
        return statusBarView
    }

    /**
     * 设置fitSystem
     */
    private fun setRootView(activity: Activity) {
        val parent =
            activity.findViewById<View>(android.R.id.content) as ViewGroup
        var i = 0
        val count = parent.childCount
        while (i < count) {
            val childView = parent.getChildAt(i)
            if (childView is ViewGroup) {
                childView.setFitsSystemWindows(true)
                childView.clipToPadding = true
            }
            i++
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

}