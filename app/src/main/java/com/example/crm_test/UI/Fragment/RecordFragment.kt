package com.example.crm_test.UI.Fragment

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crm_test.R
import com.example.crm_test.UI.activity.MainActivity
import com.example.crm_test.UI.activity.RecordDetailActivity
import com.example.crm_test.UI.viewModel.RecordFragmentViewModel
import com.example.crm_test.base.BaseFragment
import com.example.crm_test.pading.RecordPagingAdapter
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 其他人提交的记录
 * 会被返回创建头疼疼
 */
class RecordFragment : BaseFragment<RecordFragmentViewModel>() {
    /**
     * 移除已读记录
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            return
        }

        adapterRecord.notifyItemRemoved(requestCode)
    }


    override fun initData() {
        val mainActivity = requireActivity() as MainActivity
        lifecycleScope.launch {
            mFragmentViewModel.loadMes(mainActivity.baseViewModel.userId.toString())
                .collectLatest {
                    adapterRecord.submitData(it)
                }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_record
    }

    private val adapterRecord: RecordPagingAdapter by lazy {
        RecordPagingAdapter()
        { i, recordId, id ->
            val intent =
                RecordDetailActivity.jumpToRecordDetail(requireActivity(), recordId, id)
            startActivityForResult(intent, i)
        }
    }

    override fun initView() {
        my_recycler.layoutManager = LinearLayoutManager(requireActivity())
        my_recycler.adapter = adapterRecord

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun getViewModelClass(): Class<RecordFragmentViewModel> {
        return RecordFragmentViewModel::class.java
    }

}


