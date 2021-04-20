package com.nightwolf.crm_test.UI.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.UI.activity.LoginActivity
import com.nightwolf.crm_test.UI.activity.MainActivity
import com.nightwolf.crm_test.UI.viewModel.LoginViewModel
import com.nightwolf.crm_test.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * A simple [Fragment] subclass.
 */
class MyFragment : BaseFragment<LoginViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_my
    }

    override fun initView() {

        linear_login.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initData() {
        val mainActivity = requireActivity() as MainActivity
        accountName.text = mainActivity.baseViewModel.userName
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }


}
