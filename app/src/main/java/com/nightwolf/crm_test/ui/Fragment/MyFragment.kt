package com.nightwolf.crm_test.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.base.BaseFragment
import com.nightwolf.crm_test.databinding.FragmentMyBinding
import com.nightwolf.crm_test.ui.activity.LoginActivity
import com.nightwolf.crm_test.ui.activity.MainActivity
import com.nightwolf.crm_test.ui.activity.MyReportActivity
import com.nightwolf.crm_test.ui.viewModel.LoginViewModel

/**
 * A simple [Fragment] subclass.
 */
class MyFragment : BaseFragment<LoginViewModel>() {

    lateinit var binding: FragmentMyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_my
    }

    override fun initView() {

        binding.linearLogin.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.myReport.setOnClickListener {
            startActivity(Intent(requireContext(), MyReportActivity::class.java))
        }
    }

    override fun initData() {
        val mainActivity = requireActivity() as MainActivity
        binding.accountName.text = mainActivity.baseViewModel.userName
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }


}
