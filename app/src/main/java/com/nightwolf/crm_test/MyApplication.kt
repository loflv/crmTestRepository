package com.nightwolf.crm_test

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.bugly.Bugly
import java.lang.reflect.Field
import java.lang.reflect.Method

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
        Bugly.init(this, "1672ff150e", true)
        closeAndroidPDialog()
    }

    fun isDebugApp(context: Context): Boolean {
        try {
            val info = context.applicationInfo
            return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (x: java.lang.Exception) {
        }
        return false
    }


    /**
     * 初始化logger
     */
    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(1)
            .tag("logger")
            .build()
        mContext = applicationContext
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return true
            }
        })
    }

    companion object {
        lateinit var mContext: Context
    }

    /**
     * 也可调整target 版本实现
     */
    private fun closeAndroidPDialog() {
        try {
            val aClass =
                Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor =
                aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.setAccessible(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod: Method = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.setAccessible(true)
            val activityThread: Any = declaredMethod.invoke(null)
            val mHiddenApiWarningShown: Field = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.setAccessible(true)
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}