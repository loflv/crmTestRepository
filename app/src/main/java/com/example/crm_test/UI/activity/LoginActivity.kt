package com.example.crm_test.UI.activity


import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.crm_test.MyApplication
import com.example.crm_test.R
import com.example.crm_test.UI.viewModel.LoginViewModel
import com.example.crm_test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun initView() {

        if (isLogin()) return

        supportActionBar?.hide()

        clickEvent()
    }

    /**
     * 点击事件
     */
    private fun clickEvent() {
        login.setOnClickListener {
            baseViewModel.clickLogin(username.text.toString(), password.text.toString())
        }
    }

    /**
     * 判断是否登录
     */
    private fun isLogin(): Boolean {
        val cookie = MyApplication.mContext.getSharedPreferences(
            "login",
            Context.MODE_PRIVATE
        ).getString("x-ienterprise-passport", "")

        if (cookie!!.isNotBlank()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return false
    }

    /**
     * 过期重新登录时
     */
    override fun initData() {
        username.setText(
            MyApplication.mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
            ).getString("userName", "")
        )

        password.setText(
            MyApplication.mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
            ).getString("password", "")
        )
    }

    override fun startObserve() {
        super.startObserve()
        baseViewModel.uiLiveData.observe(this, Observer {
            loading.visibility = it.loadingVisible
            login.isClickable = it.clickAble
        })

        baseViewModel.loginSuccess.observe(this, Observer {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                loading.visibility = View.GONE
                finish()
            }
        })
    }

    override fun errorDeal(it: Exception) {
        super.errorDeal(it)
        Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
    }
}
