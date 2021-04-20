package com.nightwolf.crm_test.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.nightwolf.crm_test.R

import kotlinx.android.synthetic.main.basic_dialog_loading.*

/**
 * @author huanglinqing
 * @project BasicComponentLibrary
 * @date 2020/8/4
 * @desc 等待加载 对话框
 */
class LoadingDialog private constructor(mContext: Context) : Dialog(mContext), LifecycleObserver {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_dialog_loading)
        val dialogWindow = window
        dialogWindow?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    /***
     * 是否可以触摸取消 默认为false
     */
    private var canceledOnTouchOutsideValue = false

    /**
     * 是否可以点击返回 取消 默认为false
     */
    private var canceledOnBack = false

    /**
     * 背景框默认
     */
    private var dialogBackgroundResource = R.drawable.basic_button_toast

    /**
     * 底部文字颜色
     */
    private var bootomTextColor = mContext.resources.getColor(R.color.white)

    /**
     * 底部文字大小
     */
    private var bootomTextSize = 15f

    /**
     * 弹窗布局的大小 宽度
     */
    private var dialogWidth = 0f

    /**
     * 弹窗布局的大小 长度
     */
    private var dialogHeight = 0f

    /**
     * progressbar的大小
     */
    private var progressbarSize = 0f

    /**
     * 底部文字描述
     */
    private var bootomDesc: String? = null

    @Override
    override fun show() {
        if (ownerActivity != null && !ownerActivity!!.isFinishing) {
            super.show()
            show(this)
        }
    }

    /**
     * 显示对话框
     */
    @SuppressLint("ResourceAsColor")
    private fun show(loadingDialog: LoadingDialog) {

        if (!TextUtils.isEmpty(bootomDesc)) {
            tv_bootom_desc.text = bootomDesc
            tv_bootom_desc.visibility = View.VISIBLE
        }

        //屏蔽返回键
        if (!canceledOnBack) {
            setOnKeyListener { _, keyCode, _ ->
                keyCode == KeyEvent.KEYCODE_BACK
            }
        }

        setCanceledOnTouchOutside(canceledOnTouchOutsideValue)

        //设置弹窗背景
        ll_dialog.setBackgroundResource(dialogBackgroundResource)

        tv_bootom_desc.setTextColor(bootomTextColor)

        tv_bootom_desc.textSize = bootomTextSize

        if (dialogWidth != 0f) {
            val layoutParams = ll_dialog.layoutParams
            layoutParams.width = dip2px(context, dialogWidth)
            layoutParams.height = dip2px(context, dialogHeight)
            ll_dialog.layoutParams = layoutParams
        }

        if (progressbarSize != 0f) {
            val layoutParams = progressBar.layoutParams
            layoutParams.width = dip2px(context, progressbarSize)
            layoutParams.height = dip2px(context, progressbarSize)
            progressBar.layoutParams = layoutParams
        }


    }

    /**
     * dp转为px
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val r: Resources = context.applicationContext.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dipValue, r.displayMetrics
        ).toInt()
    }

    class Builder(context: Context) {

        private var loadingDialog: LoadingDialog = LoadingDialog(context)

        /**
         * 设置底部文字描述
         *
         */
        fun setBootomDesc(bootomDesc: String): Builder {
            loadingDialog.bootomDesc = bootomDesc
            return this
        }

        /**
         * 设置触摸屏幕是否消失
         */
        fun setCanceledOnTouchOutsideValue(canceled: Boolean): Builder {
            loadingDialog.canceledOnBack = canceled
            return this
        }

        /**
         * 设置是否可以点击返回键
         */
        fun setCanceledOnBackValue(onBack: Boolean): Builder {
            loadingDialog.canceledOnBack = onBack
            return this
        }

        /**
         * 设置dialog背景
         */
        fun setDialogBackground(resource: Int): Builder {
            loadingDialog.dialogBackgroundResource = resource
            return this
        }

        /**
         * 设置底部文字颜色
         */
        fun setBootomTextColor(resource: Int): Builder {
            loadingDialog.bootomTextColor = resource
            return this
        }

        /**
         * 设置底部文字大小
         */
        fun setBootomTextSize(size: Float): Builder {
            loadingDialog.bootomTextSize = size
            return this
        }

        /**
         * 设置弹窗的大小
         */
        fun setDialogSize(dialogWidth: Float, dialogHeight: Float): Builder {
            loadingDialog.dialogWidth = dialogWidth
            loadingDialog.dialogHeight = dialogHeight
            return this
        }

        /**
         * 设置progressbar的大小
         */
        fun setProgressbarSize(size: Float): Builder {
            loadingDialog.progressbarSize = size
            return this
        }


        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        fun create(): LoadingDialog {
            return loadingDialog
        }


    }

    /**
     * 取消对话框
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestory() {
        if (isShowing) {
            dismiss()
        }
    }

    init {
        if (mContext is Activity) {
            setOwnerActivity(mContext)
        }
        if (mContext is ComponentActivity) {
            mContext.lifecycle.addObserver(this)
        }
    }
}