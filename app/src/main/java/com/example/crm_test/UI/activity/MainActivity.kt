package com.example.crm_test.UI.activity

import android.content.Context
import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.crm_test.MyApplication
import com.example.crm_test.R
import com.example.crm_test.UI.viewModel.MainViewModel
import com.example.crm_test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initView() {
        val findNavController = Navigation.findNavController(
            this,
            R.id.fragment
        )
//        val appBarConfiguration = AppBarConfiguration.Builder(bottomNavigationView.menu).build()
//        bottomNavigationView.labelVisibilityMode = 1
//        NavigationUI.setupActionBarWithNavController(this, findNavController, appBarConfiguration)
        NavigationUI.setupWithNavController(bottomNavigationView, findNavController)
    }

    override fun initData() {
//        CrashHandler(this)
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
