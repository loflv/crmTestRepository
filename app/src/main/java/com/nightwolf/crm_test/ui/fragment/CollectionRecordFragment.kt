package com.nightwolf.crm_test.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.base.BaseFragment
import com.nightwolf.crm_test.databinding.FragmentCollectionRecordBinding
import com.nightwolf.crm_test.paging.CollcectionPaggingAdater
import com.nightwolf.crm_test.ui.activity.CollectionDetailActivity
import com.nightwolf.crm_test.ui.viewModel.CollectionRecordViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 *  收集内容
 */
class CollectionRecordFragment : BaseFragment<CollectionRecordViewModel>() {


    lateinit var binding: FragmentCollectionRecordBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_collection_record
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {

        val collcectionPaggingAdater = CollcectionPaggingAdater { position, id ->
            val intent =
                CollectionDetailActivity.jumpToCollectionDetail(requireContext(), id)
            startActivityForResult(intent, position)
        }
        binding.recordRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recordRecycler.adapter = collcectionPaggingAdater


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

        }).attachToRecyclerView(binding.recordRecycler)


        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            mFragmentViewModel.allCheeses.collectLatest {
                collcectionPaggingAdater.submitData(it)
            }
        }
    }

    override fun initData() {
        mFragmentViewModel.listDataSize.observe(this, Observer {
            binding.recordRecycler.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getViewModelClass(): Class<CollectionRecordViewModel> {
        return CollectionRecordViewModel::class.java
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            return
        }

        mFragmentViewModel.getList().removeAt(requestCode)
        binding.recordRecycler.adapter?.notifyItemRemoved(requestCode)
        binding.recordRecycler.adapter?.notifyItemRangeChanged(
            requestCode,
            mFragmentViewModel.getList().size
        )
    }
}
