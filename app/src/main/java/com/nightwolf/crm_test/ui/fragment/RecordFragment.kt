package com.nightwolf.crm_test.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.base.BaseFragment
import com.nightwolf.crm_test.bean.PostMesList
import com.nightwolf.crm_test.bean.global.NO_NET_WORK_ERROR
import com.nightwolf.crm_test.databinding.FragmentRecordBinding
import com.nightwolf.crm_test.paging.PagingFootAdapter
import com.nightwolf.crm_test.paging.RecordPagingAdapter
import com.nightwolf.crm_test.ui.activity.LoginActivity
import com.nightwolf.crm_test.ui.activity.RecordDetailActivity
import com.nightwolf.crm_test.ui.viewModel.MainViewModel
import com.nightwolf.crm_test.ui.viewModel.RecordFragmentViewModel
import com.nightwolf.crm_test.util.ToastUtil
import kotlinx.coroutines.flow.collectLatest

/**
 * 其他人提交的记录
 * 会被返回创建，头疼疼
 */
class RecordFragment : BaseFragment<RecordFragmentViewModel>() {

    lateinit var binding: FragmentRecordBinding

    val activityViewModel by activityViewModels<MainViewModel>()

    /**
     * 选中对象：为了返回时可以被修改
     */
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

        binding.myRecycler.layoutManager = LinearLayoutManager(requireActivity())
        //!!!! 这里要用返回的adapater
        binding.myRecycler.adapter =  adapterRecord.withLoadStateFooter(PagingFootAdapter(adapterRecord))

        binding.swipeRefresh.setOnRefreshListener {
            adapterRecord.refresh()
        }
    }

    /**
     * 移除已读记录
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            return
        }

        //如果是查看全部，则不必删除
        if (binding.lookHaveRead.isChecked) {
            return
        }

        chooseBean?.status = 1
        adapterRecord.notifyDataSetChanged()
    }


    override fun initData() {

        adapterRecord.addLoadStateListener {
            //返回错误
            when (it.refresh) {
                is LoadState.Error -> {
                    errorDeal(it.refresh)
                }
                is LoadState.NotLoading -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is LoadState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }

            when (it.append) {
                is LoadState.Error -> {
                    errorDeal(it.append)
                }
                is LoadState.NotLoading -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is LoadState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }
        }


//        adapter.loadStateFlow.collectLatest { state ->
//            mViewBinding.swipeRefresh.isRefreshing = state.refresh is LoadState.Loading


    }

    /**
     * 错误方法处理
     * @param it LoadState
     */
    private fun errorDeal(it: LoadState) {

        val message = (it as LoadState.Error).error.message
        ToastUtil.show(message)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_record
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(layoutInflater)

        binding.lookHaveRead.setOnCheckedChangeListener { _, isChecked ->
            adapterRecord.seeHaveRead = isChecked
            loadMes(
                if (binding.lookHaveRead.isChecked) {
                    0
                } else {
                    1
                }
            )
        }
        loadMes()
        return binding.root
    }

    /**
     * 加载数据
     * @param isRead Int
     */
    private fun loadMes(isRead: Int = 0) {
        lifecycleScope.launchWhenResumed {
            mFragmentViewModel.loadMes(
                activityViewModel.userId.toString(), isRead
            ).collectLatest {
                adapterRecord.submitData(it)
            }
        }
    }


    override fun getViewModelClass(): Class<RecordFragmentViewModel> {
        return RecordFragmentViewModel::class.java
    }


}


