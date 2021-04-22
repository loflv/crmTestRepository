package com.nightwolf.crm_test.ui.viewModel

import android.content.Context
import androidx.lifecycle.asLiveData
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.api.CrmApi
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.util.NetWorkUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FeedViewModel : BaseViewModel() {


    var userId = MyApplication.mContext.getSharedPreferences(
        "login",
        Context.MODE_PRIVATE
    ).getLong("passport_id", 0)

    fun getFeedBack() = flow {
        val otherReply = NetWorkUtils.phoneRetrofitService(CrmApi::class.java).getFeed(
            20, 1, 0, userId.toString(), "2010.7"
        )
        emit(otherReply.body?.atList)
    }.catch {
        Logger.e(it.message.toString())
    }.asLiveData()
}