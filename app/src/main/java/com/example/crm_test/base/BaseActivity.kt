package com.example.crm_test.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.Logger

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {


    lateinit var baseViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseViewModel = getViewModelClass().let {
            ViewModelProvider(this).get(it)
        }

        setContentView(getLayoutId())

        initData()
        initView()
        startObserve()

    }


    abstract fun getLayoutId(): Int

    abstract fun getViewModelClass(): Class<VM>

    abstract fun initView()

    abstract fun initData()

    open fun startObserve() {
        baseViewModel.errorLiveData.observe(this, Observer {
            errorDeal(it)
        })
    }

    open fun errorDeal(it: Exception) {
        Logger.e(it.message.toString())
    }

}