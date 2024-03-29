package com.nightwolf.crm_test.ui.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.base.BaseActivity
import com.nightwolf.crm_test.databinding.ActivityLoginBinding
import com.nightwolf.crm_test.ui.viewModel.LoginViewModel


/**
 * 登录界面
 */
class LoginActivity : BaseActivity<LoginViewModel>() {

    lateinit var binding: ActivityLoginBinding


    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

     fun initView() {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        clickEvent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
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
     fun initData() {
        val phone = MyApplication.mContext.getSharedPreferences(
            "login",
            Context.MODE_PRIVATE
        ).getString("userName", "")
        binding.username.setText(
            phone
        )
        binding.username.setSelection(phone?.length ?: 0)

        binding.password.setText(
            MyApplication.mContext.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
            ).getString("password", "")
        )

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


}
