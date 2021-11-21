package com.nightwolf.crm_test.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.base.BaseActivity
import com.nightwolf.crm_test.databinding.ActivityMainBinding
import com.nightwolf.crm_test.ext.setBadge
import com.nightwolf.crm_test.ui.viewModel.MainViewModel

class MainActivity : BaseActivity<MainViewModel>() {

    lateinit var binding: ActivityMainBinding


    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

     fun initView() {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    /**
     * 初始化导航器
     */
    private fun initNavigation() {
        val findNavController = Navigation.findNavController(
            this,
            R.id.fragment
        )

        //取消toast弹窗
        val listOf = listOf(
            R.id.recordFragment,
            R.id.collectionRecord,
            R.id.myFragment,
            R.id.feedFragment
        )
        listOf.forEach {
            binding.bottomNavigationView!![0].findViewById<View>(it).setOnLongClickListener {
                true
            }
        }

        //避免重复点击
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {

        }
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {

        }
        NavigationUI.setupWithNavController(binding.bottomNavigationView, findNavController)
        binding.bottomNavigationView.labelVisibilityMode = LABEL_VISIBILITY_LABELED
    }

     fun initData() {
        baseViewModel.isLogin().observe(this, {
            if (!it) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

         //设置未读角标
         baseViewModel.getFeedBack().observe(this,{
             binding.bottomNavigationView.setBadge(1,it?.size?:0)
         })


    }


}
