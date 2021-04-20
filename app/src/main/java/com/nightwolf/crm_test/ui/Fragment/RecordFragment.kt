package com.nightwolf.crm_test.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.base.BaseFragment
import com.nightwolf.crm_test.bean.PostMesList
import com.nightwolf.crm_test.databinding.FragmentRecordBinding
import com.nightwolf.crm_test.pading.RecordPagingAdapter
import com.nightwolf.crm_test.ui.activity.MainActivity
import com.nightwolf.crm_test.ui.activity.RecordDetailActivity
import com.nightwolf.crm_test.ui.viewModel.RecordFragmentViewModel
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
        binding.swipeRefresh.isRefreshing = true
        val mainActivity = requireActivity() as MainActivity
        lifecycleScope.launch {
            mFragmentViewModel.loadMes(mainActivity.baseViewModel.userId.toString())
                .collectLatest {
                    adapterRecord.submitData(it)
                }
        }
        //监听刷新状态当刷新完成之后关闭刷新
        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapterRecord.loadStateFlow.collectLatest {
                if (it.refresh !is LoadState.Loading) {
                    binding.swipeRefresh.isRefreshing = false
                    binding.waitReadText.text = "无等待审阅的项目"
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    lateinit var binding: FragmentRecordBinding

    override fun initView() {

        binding.myRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.myRecycler.adapter = adapterRecord

        binding.swipeRefresh.setOnRefreshListener {
            adapterRecord.refresh()
        }
    }

    override fun getViewModelClass(): Class<RecordFragmentViewModel> {
        return RecordFragmentViewModel::class.java
    }

}

