package com.nightwolf.crm_test.ui.viewModel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.api.CrmApi
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.bean.LoginBean
import com.nightwolf.crm_test.util.Base64Utils
import com.nightwolf.crm_test.util.RSAUtils
import com.nightwolf.crm_test.util.RetrofitUtils
import com.nightwolf.crm_test.util.SharedPreferencesRepository
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
            val retrofit1 = RetrofitUtils.createRetrofitService(
                CrmApi::class.java,
                "https://crm.xiaoshouyi.com/auc/",
                true
            )
            val retrofit = RetrofitUtils.createRetrofitService(
                CrmApi::class.java,
                "https://crm.xiaoshouyi.com/auc/",
                false
            )
            //获取key
            retrofit1.mainIndex(
                "https://crm.xiaoshouyi.com/mobile/auc/loginSupport.action?&os=28&_vs=2010.7" +
                        "&imei=865472047803957&app_type=0&login_type=mobile&source=1&model=HUAWEI%20HWJKM-H"
            )

            val keyBean = retrofit.getKey(
                "https://login.xiaoshouyi.com/auc/passport/password-key"
            )
            val ss =
                RSAUtils.encryptByPublicKey(password.trim().toByteArray(), keyBean.result?.key)
            val encode = Base64Utils.encode(ss).trim().replace("\n", "").replace("\r", "")
            Log.d("ssss  解密后数据  ", encode)

            //登录
            val login = retrofit.login(
                "https://login.xiaoshouyi.com/auc/login",
                username,
                encode,
                "web"
            )

            //登录失败
            if (login.result !is LoginBean.ResultBean) {
                uiLiveData.value = LoginUi(true, View.GONE)
                return@launch
            }

            //保存信息 todo
            SharedPreferencesRepository.putContent {
                putLong("passport_id", login.result!!.passport_id)
                commit()
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
                putLong("userId", lastToken.result?.mobileAuthData?.result?.userId ?: 0)
                commit()
            }

            loginSuccess.value = true

        }

    }

    data class LoginUi(var clickAble: Boolean = true, var loadingVisible: Int = View.GONE)
}