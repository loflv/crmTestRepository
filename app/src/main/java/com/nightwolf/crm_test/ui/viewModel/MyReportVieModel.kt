package com.nightwolf.crm_test.ui.viewModel

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.paging.MyRecordDataSource

class MyReportVieModel : BaseViewModel() {

    var userId = MyApplication.mContext.getSharedPreferences(
        "login",
        Context.MODE_PRIVATE
    ).getLong("passport_id", 0)

    fun loadMes() = Pager(PagingConfig(20, initialLoadSize = 20)) {
        MyRecordDataSource(userId.toString())
    }.flow.cachedIn(viewModelScope)
}