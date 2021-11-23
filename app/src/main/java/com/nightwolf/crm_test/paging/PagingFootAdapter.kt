package com.nightwolf.crm_test.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nightwolf.crm_test.databinding.ItemLoadFootLayoutBinding

/**
 * paging 底部加载栏
 */
class PagingFootAdapter(val adapterRecord: RecordPagingAdapter) :
    LoadStateAdapter<PagingFootAdapter.LoadFootViewHolder>() {

    class LoadFootViewHolder(val binding: ItemLoadFootLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: LoadFootViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                holder.binding.loadingGroup.visibility = View.VISIBLE
                holder.binding.button.visibility = View.GONE
            }
            is LoadState.Error -> {
                holder.binding.loadingGroup.visibility = View.GONE
                holder.binding.button.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadFootViewHolder {
        val loadFootViewHolder = LoadFootViewHolder(
            ItemLoadFootLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        loadFootViewHolder.binding.button.setOnClickListener {
            adapterRecord.refresh()
        }
        return loadFootViewHolder
    }
}