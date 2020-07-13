package com.example.crm_test.UI.viewModel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.crm_test.MyApplication
import com.example.crm_test.api.CrmApi
import com.example.crm_test.base.BaseViewModel
import com.example.crm_test.bean.LoginBean
import com.example.crm_test.util.NetWorkUtils
import com.example.crm_test.util.SharedPreferencesRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class LoginViewModel : BaseViewModel() {

    var uiLiveData = MutableLiveData<LoginUi>()

    val loginSuccess = MutableLiveData<Boolean>()

    init {
        loginSuccess.value = false
    }

    fun clickLogin(username: String, password: String) {
        uiLiveData.value = LoginUi(false, View.VISIBLE)
        launch {
            val retrofit1 = NetWorkUtils.createRetrofitService(CrmApi::class.java, true)
            val retrofit = NetWorkUtils.createRetrofitService(CrmApi::class.java, false)
            //获取key
            retrofit1.mainIndex(
                "https://crm.xiaoshouyi.com/mobile/auc/loginSupport.action?&os=28&_vs=2006.2" +
                        "&imei=865472047803957&app_type=0&login_type=mobile&source=1&model=HUAWEI%20HWJKM-H"
            )
            //登录
            val login = retrofit.login(
                "https://login.xiaoshouyi.com/auc/login",
                username,
                password,
                "web"
            )

            //登录失败
            if (login.result !is LoginBean.ResultBean) {
                uiLiveData.value = LoginUi(true, View.GONE)
                errorLiveData.value = Exception(login.error_msg)
                return@launch
            }

            //保存信息 todo
            SharedPreferencesRepository.putContent {
                putLong("passport_id", login.result!!.passport_id)
                apply()
            }
            val mapOf = mapOf(
                "user_type" to "0",
                "encryption_key" to login.result!!.tenant_list!![0]!!.encryptionKey!!,
                "tenant_id" to login.result!!.tenant_list!![0].id!!,
                "passport_id" to login.result!!.passport_id.toString()
            )
            val authorizeCode = retrofit.getAuthorizeCode(
                "https://login.xiaoshouyi.com/auc/oauth2/authorize-code",
                Gson().toJson(mapOf)
                    .toRequestBody("application/json;charset=utf-8".toMediaType())
            )

            val lastToken = retrofit.getLastToken(authorizeCode.result!!.redirectUrl)

            Toast.makeText(MyApplication.mContext, "登录成功", Toast.LENGTH_SHORT).show()

            SharedPreferencesRepository.putContent {
                putString(
                    "x-ienterprise-passport",
                    lastToken.result?.mobileAuthData?.result?.token
                )
                putString("userName", username)
                putString("password", password)
                putString("accountName", lastToken.result?.mobileAuthData?.result?.userName)
                apply()
            }

            loginSuccess.value = true

        }

    }

    data class LoginUi(var clickAble: Boolean = true, var loadingVisible: Int = View.GONE)
}