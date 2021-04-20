package com.nightwolf.crm_test.util

import android.content.Context
import com.nightwolf.crm_test.ui.LoadingDialog

object DialogLoadingUtils {

    var dialog: LoadingDialog? = null

    /**
     *@author liwu
     *@date 20-9-24
     *@describe   显示等待框
     */
    fun showLoading(context: Context, mes: String) {

        if (dialog != null && dialog!!.isShowing) {
            dialog!!.cancel()
        }

        dialog = LoadingDialog.Builder(context)
            .setDialogSize(120f, 112f)
            .setBootomDesc(mes)
            .create()
        dialog!!.show()
    }

    /**
     *@author liwu
     *@date 20-9-24
     *@describe  取消等待弹窗
     */
    fun cancel() {
        dialog?.cancel()
    }
}