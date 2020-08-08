package com.example.crm_test.util

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Environment
import com.orhanobut.logger.Logger
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


class CrashHandler(val context: Activity) : Thread.UncaughtExceptionHandler {

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        dumpExceptionToSDCard(e)
        context.finish()
        exitProcess(0)

    }

    @SuppressLint("SimpleDateFormat")
    private fun dumpExceptionToSDCard(e: Throwable) {
        Logger.d("重启了")
        val externalFilesDir =
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath
        val currentTime = System.currentTimeMillis()
        val time: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(currentTime))
        val path = externalFilesDir + File.separator + "CrashInfo/crash_${time}.txt"
        val file = File(path)
        val dir = File(file.parent)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        if (!file.exists()) {
            file.createNewFile()
        }
        try {
            val printWriter = PrintWriter(BufferedWriter(FileWriter(file)))
            printWriter.println(time)
            dumpPhoneInfo(printWriter)
            printWriter.println()
            e.printStackTrace(printWriter)
            printWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun dumpPhoneInfo(printWriter: PrintWriter) {
        //系统版本号
        printWriter.print("OS Version:")
        printWriter.print(Build.VERSION.RELEASE)
        printWriter.print("_")
        printWriter.println(Build.VERSION.SDK_INT)
        //硬件制造商
        printWriter.print("Vendor:")
        printWriter.println(Build.MANUFACTURER)
        //系统定制商
        printWriter.print("Brand:")
        printWriter.println(Build.BRAND)
    }
}