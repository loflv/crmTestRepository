package com.nightwolf.crm_test.ui.viewModel

import android.content.Context
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    var userId = MyApplication.mContext.getSharedPreferences(
        "login",
        Context.MODE_PRIVATE
    ).getLong("passport_id", 0)

    var userName: String =
        MyApplication.mContext.getSharedPreferences("login", Context.MODE_PRIVATE)
            .getString("accountName", "")!!
}