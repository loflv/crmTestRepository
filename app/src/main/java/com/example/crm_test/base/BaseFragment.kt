package com.example.crm_test.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.Logger


abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    lateinit var mFragmentViewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(getLayoutId(), null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getViewModelClass().let {
            mFragmentViewModel = ViewModelProvider(this).get(it)
        }
        initView()
        initData()
        startObserve()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()
    abstract fun initData()

    abstract fun getViewModelClass(): Class<VM>

    open fun startObserve() {
        mFragmentViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            onError(it)
        })
    }

    open fun onError(e: Throwable) {
        Logger.e(e.message.toString())
    }
}