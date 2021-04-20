package com.nightwolf.crm_test.UI.Fragment

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.UI.activity.CollectionDetailActivity
import com.nightwolf.crm_test.UI.viewModel.CollectionRecordViewModel
import com.nightwolf.crm_test.base.BaseFragment
import com.nightwolf.crm_test.pading.CollcectionPaggingAdater
import kotlinx.android.synthetic.main.fragment_collection_record.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 *  收集内容
 */
class CollectionRecordFragment : BaseFragment<CollectionRecordViewModel>() {



    override fun getLayoutId(): Int {
        return R.layout.fragment_collection_record
    }


    override fun initView() {

        val collcectionPaggingAdater = CollcectionPaggingAdater { position, id ->
            val intent =
                CollectionDetailActivity.jumpToCollectionDetail(requireContext(), id)
            startActivityForResult(intent, position)
        }
        record_recycler.layoutManager = LinearLayoutManager(requireContext())
        record_recycler.adapter = collcectionPaggingAdater


        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CollcectionPaggingAdater.ColleactionViewHolder).mRoom?.let {
                    mFragmentViewModel.remove(it)
                }
            }

        }).attachToRecyclerView(record_recycler)


        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            mFragmentViewModel.allCheeses.collectLatest {
                collcectionPaggingAdater.submitData(it)
            }
        }
    }

    override fun initData() {
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
        record_recycler.adapter?.notifyItemRangeChanged(
            requestCode,
            mFragmentViewModel.getList().size
        )
    }
}
