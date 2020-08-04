package com.example.crm_test.UI.Fragment

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crm_test.R
import com.example.crm_test.UI.activity.MainActivity
import com.example.crm_test.UI.activity.RecordDetailActivity
import com.example.crm_test.UI.viewModel.RecordFragmentViewModel
import com.example.crm_test.base.BaseFragment
import com.example.crm_test.pading.PagingAdapter
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

        mFragmentViewModel.getList().removeAt(requestCode)
        my_recycler.adapter?.notifyItemRemoved(requestCode)
        my_recycler.adapter?.notifyItemRangeChanged(requestCode, mFragmentViewModel.getList().size)
    }


    override fun initData() {
        val mainActivity = requireActivity() as MainActivity
        lifecycleScope.launch {
            mFragmentViewModel.loadMes(mainActivity.baseViewModel.userId.toString()).collectLatest {
                adapter.submitData(it)
            }
        }


    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_record
    }

    private val adapter: PagingAdapter by lazy {
        PagingAdapter()
        { i, recordId, id ->
            val intent =
                RecordDetailActivity.jumpToRecordDetail(requireActivity(), recordId, id)
            startActivityForResult(intent, i)
        }
    }


    override fun initView() {
        my_recycler.layoutManager = LinearLayoutManager(requireActivity())
        my_recycler.adapter = adapter
/*        my_recycler.adapter = PagingAdapter()
        { i, recordId, id ->
            val intent =
                RecordDetailActivity.jumpToRecordDetail(requireActivity(), recordId, id)
            startActivityForResult(intent, i)
        }*/

        /*RecordAdapter(
            mFragmentViewModel.getList(),
            { initData() },
            { i, recordId, id ->
                val intent =
                    RecordDetailActivity.jumpToRecordDetail(requireActivity(), recordId, id)
                startActivityForResult(intent, i)
            })*/

        /*my_recycler.layoutManager = LinearLayoutManager(requireActivity())
        my_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (my_recycler.layoutManager as LinearLayoutManager).itemCount ==
                    (my_recycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                ) {
                    initData()
                }
            }
        })
*/
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun getViewModelClass(): Class<RecordFragmentViewModel> {
        return RecordFragmentViewModel::class.java
    }

    override fun startObserve() {
        super.startObserve()
//        mFragmentViewModel.recyclerListSize.observe(this, Observer {
//            my_recycler.adapter!!.notifyDataSetChanged()
//        })

//        mFragmentViewModel.number.observe(this, Observer {
//            if (it > 3) {
//                val recordAdapter = my_recycler.adapter as RecordAdapter
//                recordAdapter.hideFootViewHolder()
//            }
//        })
    }
}


