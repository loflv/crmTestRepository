package com.nightwolf.crm_test.ui.viewModel

import android.content.Context
import androidx.lifecycle.asLiveData
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.api.CrmApi
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.util.RetrofitUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MainViewModel : BaseViewModel() {

    var userId = MyApplication.mContext.getSharedPreferences(
        "login",
        Context.MODE_PRIVATE
    ).getLong("passport_id", 0)

    var userName: String =
        MyApplication.mContext.getSharedPreferences("login", Context.MODE_PRIVATE)
            .getString("accountName", "")!!

    /**
     * 判断是否登录
     * @return LiveData<Boolean?>
     */
    fun isLogin() = flow {
        val cookie = MyApplication.mContext.getSharedPreferences(
            "login",
            Context.MODE_PRIVATE
        ).getString("x-ienterprise-passport", "")

        emit(cookie?.isNotBlank() ?: false)
    }.asLiveData()


    fun getFeedBack() = flow {
        val otherReply = RetrofitUtils.createRetrofitService(CrmApi::class.java).getFeed(
            20, 1, 1, userId.toString(), "2010.7"
        )
        emit(otherReply.body?.atList)
    }.catch {
        Logger.e(it.message.toString())
    }.asLiveData()
}