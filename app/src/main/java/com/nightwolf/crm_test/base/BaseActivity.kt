package com.nightwolf.crm_test.base

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nightwolf.crm_test.util.StatusBarUtils

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {


    lateinit var baseViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseViewModel = getViewModelClass().let {
            ViewModelProvider(this).get(it)
        }

        StatusBarUtils.setColor(this, Color.parseColor("#AABF68"))

    }


    abstract fun getViewModelClass(): Class<VM>



}