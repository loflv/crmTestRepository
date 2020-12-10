package com.example.crm_test.UI.Fragment

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crm_test.R
import com.example.crm_test.UI.activity.MainActivity
import com.example.crm_test.UI.activity.RecordDetailActivity
import com.example.crm_test.UI.viewModel.RecordFragmentViewModel
import com.example.crm_test.base.BaseFragment
import com.example.crm_test.bean.PostMesList
import com.example.crm_test.pading.RecordPagingAdapter
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

        chooseBean?.status = 1
        adapterRecord.notifyDataSetChanged()

        //?? adapterRecord 的remove事件
    }


    override fun initData() {
        swipeRefresh.isRefreshing = true
        val mainActivity = requireActivity() as MainActivity
        lifecycleScope.launch {
            mFragmentViewModel.loadMes(mainActivity.baseViewModel.userId.toString())
                .collectLatest {
                    adapterRecord.submitData(it)
                }
        }
        //监听刷新状态当刷新完成之后关闭刷新
        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapterRecord.loadStateFlow.collectLatest {
                if (it.refresh !is LoadState.Loading) {
                    swipeRefresh.isRefreshing = false
                    wait_read_text.text = "无等待审阅的项目"
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_record
    }

    var chooseBean: PostMesList.BodyBean.NoticesBean? = null
    private val adapterRecord: RecordPagingAdapter by lazy {
        RecordPagingAdapter()
        { i, recordId, bean ->
            chooseBean = bean
            val intent =
                RecordDetailActivity.jumpToRecordDetail(requireActivity(), recordId, bean.id)
            startActivityForResult(intent, i)
        }
    }

    override fun initView() {
        my_recycler.layoutManager = LinearLayoutManager(requireActivity())
        my_recycler.adapter = adapterRecord

        swipeRefresh.setOnRefreshListener {
            adapterRecord.refresh()
        }
    }

    override fun getViewModelClass(): Class<RecordFragmentViewModel> {
        return RecordFragmentViewModel::class.java
    }

}


