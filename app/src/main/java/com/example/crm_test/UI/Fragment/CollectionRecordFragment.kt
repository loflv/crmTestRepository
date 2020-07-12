package com.example.crm_test.UI.Fragment

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crm_test.R
import com.example.crm_test.UI.activity.CollectionDetailActivity
import com.example.crm_test.UI.viewModel.CollectionRecordViewModel
import com.example.crm_test.adapter.ColleactionAdatper
import com.example.crm_test.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_collection_record.*


/**
 *  收集内容
 */
class CollectionRecordFragment : BaseFragment<CollectionRecordViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_collection_record
    }

    override fun initView() {
        record_recycler.layoutManager = LinearLayoutManager(requireContext())
        record_recycler.adapter =
            ColleactionAdatper(requireActivity(), mFragmentViewModel.getList()) { position, id ->
                val intent =
                    CollectionDetailActivity.jumpToCollectionDetail(requireContext(), id)
                startActivityForResult(intent, position)
            }
    }

    override fun initData() {
        mFragmentViewModel.findAll()
    }

    override fun getViewModelClass(): Class<CollectionRecordViewModel> {
        return CollectionRecordViewModel::class.java
    }

    override fun startObserve() {
        super.startObserve()
        mFragmentViewModel.listDataSize.observe(this, Observer {
            record_recycler.adapter!!.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            return
        }

        mFragmentViewModel.getList().removeAt(requestCode)
        record_recycler.adapter?.notifyItemRemoved(requestCode)
        record_recycler.adapter?.notifyItemRangeChanged(requestCode, mFragmentViewModel.getList().size)
    }
}
