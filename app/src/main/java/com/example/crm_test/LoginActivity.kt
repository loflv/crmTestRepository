package com.example.crm_test


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crm_test.api.CrmApi
import com.example.crm_test.bean.LoginBean
import com.example.crm_test.util.MyApplication
import com.example.crm_test.util.NetWorkUtils
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        username.setText(
            MyApplication.mContext.getSharedPreferences(
                "default",
                Context.MODE_PRIVATE
            ).getString("", "")
        )

        password.setText(
            MyApplication.mContext.getSharedPreferences(
                "default",
                Context.MODE_PRIVATE
            ).getString("", "")
        )

        val cookie = MyApplication.mContext.getSharedPreferences(
            "default",
            Context.MODE_PRIVATE
        ).getString("x-ienterprise-passport", "")

        if (cookie!!.isNotBlank()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        login.setOnClickListener {

            loading.visibility = View.VISIBLE
            login.isClickable = false

            val retrofit1 = NetWorkUtils.createRetrofitService(CrmApi::class.java, true)
            val retrofit = NetWorkUtils.createRetrofitService(CrmApi::class.java, false)

            retrofit1.mainIndex("https://crm.xiaoshouyi.com/mobile/auc/loginSupport.action?&os=28&_vs=2006.2&imei=865472047803957&app_type=0&login_type=mobile&source=1&model=HUAWEI%20HWJKM-H")
                .subscribeOn(Schedulers.newThread())
                .flatMap {
                    retrofit.login(
                        "https://login.xiaoshouyi.com/auc/login",
                        username.text.toString(),
                        password.text.toString(),
                        "web"
                    )
                }.flatMap {

                    if (it.result !is LoginBean.ResultBean) {
                        Observable.error<String>(Exception(it.error_msg))
                    }

                    MyApplication.mContext.getSharedPreferences("default", Context.MODE_PRIVATE)
                        .edit()
                        .putLong("passport_id", it.result!!.passport_id)
                        .putString("encryptionKey", it.result!!.tenant_list!![0].encryptionKey)
                        .putString("id", it.result!!.tenant_list!![0].id)
                        .apply()

                    val mapOf = mapOf(
                        "user_type" to "0",
                        "encryption_key" to it.result!!.tenant_list!![0]!!.encryptionKey!!,
                        "tenant_id" to it.result!!.tenant_list!![0].id!!,
                        "passport_id" to it.result!!.passport_id.toString()
                    )
                    retrofit.getAuthorizeCode(
                        "https://login.xiaoshouyi.com/auc/oauth2/authorize-code",
                        Gson().toJson(mapOf)
                            .toRequestBody("application/json;charset=utf-8".toMediaType())
                    )
                }
                .flatMap {
                    retrofit.LoginLast(it.result!!.redirectUrl)
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()

                    MyApplication.mContext.getSharedPreferences(
                        "default",
                        Context.MODE_PRIVATE
                    ).edit()
                        .putString(
                            "x-ienterprise-passport",
                            it.result?.mobileAuthData?.result?.token
                        ).putString("userName", username.text.toString())
                        .putString("password", password.text.toString())
                        .apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    loading.visibility = View.GONE
                    finish()
                }, {
                    loading.visibility = View.GONE
                    login.isClickable = true
                    Logger.e(it.message.toString())
                    Snackbar.make(login, "错误提示:" + it.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                })


        }

    }
}
