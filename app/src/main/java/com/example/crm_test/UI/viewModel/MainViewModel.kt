package com.example.crm_test.UI.viewModel

import android.content.Context
import com.example.crm_test.MyApplication
import com.example.crm_test.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    var userId = MyApplication.mContext.getSharedPreferences(
        "login",
        Context.MODE_PRIVATE
    ).getLong("passport_id", 0)

    var userName: String =
        MyApplication.mContext.getSharedPreferences("login", Context.MODE_PRIVATE)
            .getString("accountName", "")!!
}