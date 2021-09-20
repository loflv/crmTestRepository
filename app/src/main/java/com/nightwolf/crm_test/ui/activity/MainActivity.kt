package com.nightwolf.crm_test.ui.activity

import android.content.Context
import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.base.BaseActivity
import com.nightwolf.crm_test.databinding.ActivityMainBinding
import com.nightwolf.crm_test.ui.viewModel.MainViewModel

class MainActivity : BaseActivity<MainViewModel>() {

    lateinit var binding: ActivityMainBinding
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initView() {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val findNavController = Navigation.findNavController(
            this,
            R.id.fragment
        )
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            
        }
        NavigationUI.setupWithNavController(binding.bottomNavigationView, findNavController)
        binding.bottomNavigationView.labelVisibilityMode = 1
    }

    override fun initData() {
        isLogin()
    }


    /**
     * 判断是否登录
     */
    private fun isLogin(): Boolean {
        val cookie = MyApplication.mContext.getSharedPreferences(
            "login",
            Context.MODE_PRIVATE
        ).getString("x-ienterprise-passport", "")

        if (cookie!!.isBlank()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return false
    }

}
