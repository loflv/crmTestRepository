/*
 * Copyright (C),2007-2020, LonBon Technologies Co. Ltd. All Rights Reserved.
 */

package com.nightwolf.crm_test.util

import android.content.Context
import android.text.SpannableString
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.R


/**
 * @author liwu
 * @create 20-7-28
 * @describe Toast的工具类
 *
 */
object ToastUtil {

    var sToast: Toast? = null

    /**
     * @param duration 默认短时间显示
     *
     * 1. 通常用法：
     *        ToastUtil.show(content,"显示内容")
     *
     * 2.如果需要设置长时间显示
     *         ToastUtil.show(content,"显示内容",duration = Toast.LENGTH_LONG)
     *
     */
    fun show(context: Context, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        sToast?.cancel()
        sToast = Toast.makeText(context, message, duration).apply {
            show()
        }
    }

    fun show(
        message: String?,
        context: Context = MyApplication.mContext,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        show(context, message, duration)
    }


    /**
     * 展现一个居中的toast：美化版
     * @param text 显示的内容
     * @param textAlignment text的居中方式
     * @param gravity toast的显示位置
     * @param yOffset toast上下的偏移量
     * @param during 默认显示时长
     *
     * 1.通常用法 默认时间短：
     *         ToastUtil.beautyToastShow(context,"显示内容")
     *
     * 2. 长时间显示：
     *         ToastUtil.beautyToastShow(context,"显示内容",duration = Toast.LENGTH_LONG)
     *
     * 3. toast中文本排版改变:默认文字靠左显示
     *         ToastUtil.beautyToastShow(context,"显示内容",textAlignment = View.TEXT_ALIGNMENT_CENTER)
     *
     * 4. toast显示位置改变：默认屏幕正中
     *         ToastUtil.beautyToastShow(context,"显示内容",gravity = Gravity.BOTTOM)
     * 下面边距通过yoffset控制
     *         ToastUtil.beautyToastShow(context,"显示内容",gravity = Gravity.BOTTOM,yOffset = -30)
     *
     * 5.需要修改文字大小，背景颜色，padding或者其他相关属性可以通过
     *   getDefaultView(context: Context, string: String) 或者自定义view的方式 来展示
     *
    val view = getDefaultView(context, "显示内容").apply {
    setBackgroundResource(R.drawable.basic_button_toast)
    textSize = 16F
    }

    ToastUtil.beautyToastShow(context, view = view)
     */
    fun beautyToastShow(
        context: Context,
        text: String = "",
        view: View = getDefaultView(context, text),
        gravity: Int = Gravity.CENTER,
        yOffset: Int = 0,
        textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START,
        during: Int = Toast.LENGTH_SHORT
    ) {
        view.textAlignment = textAlignment
        sToast?.cancel()
        sToast = Toast(context).apply {
            this.view = view
            this.duration = during
            setGravity(gravity, 0, yOffset)
            show()
        }
    }

    fun beautyToastShow(
        context: Context,
        text: SpannableString,
        view: View = getDefaultView(context, text),
        gravity: Int = Gravity.CENTER,
        yOffset: Int = 0,
        textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START,
        during: Int = Toast.LENGTH_SHORT
    ) {
        view.textAlignment = textAlignment
        sToast?.cancel()
        sToast = Toast(context).apply {
            this.view = view
            this.duration = during
            setGravity(gravity, 0, yOffset)
            show()
        }
    }

    fun beautyToastShow(
        text: String = "",
        context: Context = MyApplication.mContext,
        view: View = getDefaultView(context, text),
        gravity: Int = Gravity.CENTER,
        yOffset: Int = 0,
        textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START,
        during: Int = Toast.LENGTH_SHORT
    ) {
        beautyToastShow(context, text, view, gravity, yOffset, textAlignment, during)
    }

    fun beautySpanToastShow(
        text: SpannableString,
        context: Context = MyApplication.mContext,
        view: View = getDefaultView(context, text),
        gravity: Int = Gravity.CENTER,
        yOffset: Int = 0,
        textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START,
        during: Int = Toast.LENGTH_SHORT
    ) {
        beautyToastShow(context, text, view, gravity, yOffset, textAlignment, during)
    }

    /**
     * 默认展示的toastView
     * @param string 默认展示的文字
     */
    private fun getDefaultView(context: Context, string: String): TextView {
        return TextView(context).apply {
            text = string
            setBackgroundResource(R.drawable.basic_button_toast)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            textSize = 16F
            setTextColor(-0x21000001)
            val padding13 = 26
            val padding22 = 44
            setPadding(padding22, padding13, padding22, padding13)
        }
    }

    private fun getDefaultView(context: Context, string: SpannableString): TextView {
        return TextView(context).apply {
            text = string
            setBackgroundResource(R.drawable.basic_button_toast)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            textSize = 16F
            setTextColor(-0x21000001)
            val padding13 = 26
            val padding22 = 44
            setPadding(padding22, padding13, padding22, padding13)
        }
    }

}