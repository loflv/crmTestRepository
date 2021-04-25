package com.nightwolf.crm_test.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.base.BaseActivity
import com.nightwolf.crm_test.databinding.ActivityMyReportBinding
import com.nightwolf.crm_test.pading.MyRecordPagingAdapter
import com.nightwolf.crm_test.ui.viewModel.MyReportVieModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyReportActivity : BaseActivity<MyReportVieModel>() {


    lateinit var binding: ActivityMyReportBinding
    override fun getLayoutId(): Int {
        return R.layout.activity_my_report
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myRecycler.layoutManager = LinearLayoutManager(this)
        val myRecordPagingAdapter = MyRecordPagingAdapter { _, _, bean ->
            val intent =
                RecordDetailActivity.jumpToRecordDetail(this, recordId, bean.id)
            startActivityForResult(intent, i)
        }
        binding.myRecycler.adapter = myRecordPagingAdapter

        lifecycleScope.launch {
            baseViewModel.loadMes().collectLatest {
                myRecordPagingAdapter.submitData(it)
            }
        }

    }

    override fun getViewModelClass(): Class<MyReportVieModel> {
        return MyReportVieModel::class.java
    }

    override fun initView() {


    }

    override fun initData() {
    }
}