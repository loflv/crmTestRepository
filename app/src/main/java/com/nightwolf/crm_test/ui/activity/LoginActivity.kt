package com.nightwolf.crm_test.ui.activity


import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.base.BaseActivity
import com.nightwolf.crm_test.databinding.ActivityLoginBinding
import com.nightwolf.crm_test.ui.viewModel.LoginViewModel


class LoginActivity : BaseActivity<LoginViewModel>() {

    lateinit var binding: ActivityLoginBinding
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun initView() {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        clickEvent()
    }

    /**
     * 点击事件
     */
    private fun clickEvent() {
        binding.login.setOnClickListener {
            baseViewModel.clickLogin(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }
    }


    /**
     * 过期重新登录时
     */
    override fun initData() {
        binding.username.setText(
            MyApplication.mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
            ).getString("userName", "")
        )

        binding.password.setText(
            MyApplication.mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
            ).getString("password", "")
        )
    }

    override fun startObserve() {
        super.startObserve()
        baseViewModel.uiLiveData.observe(this, Observer {
            binding.loading.visibility = it.loadingVisible
            binding.login.isClickable = it.clickAble
        })

        baseViewModel.loginSuccess.observe(this, Observer {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                binding.loading.visibility = View.GONE
                finish()
            }
        })
    }

    override fun errorDeal(it: Exception) {
        super.errorDeal(it)
        Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
    }
}
