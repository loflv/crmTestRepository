package com.nightwolf.crm_test.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nightwolf.crm_test.base.FeedBean
import com.nightwolf.crm_test.databinding.FeedLayoutBinding
import com.nightwolf.crm_test.util.atColor

class FeedAdapter(val list: MutableList<FeedBean.BodyBean.AtListBean>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            FeedLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val atListBean = list[position]
        holder.binding.name.text = atListBean.comment?.user?.name + ":"
        if (atListBean.status == 0) {
            holder.binding.name.setTextColor(Color.parseColor("#FFEB3B"))
        } else {
            holder.binding.name.setTextColor(Color.parseColor("#000000"))
        }
        holder.binding.content.text = atListBean.comment?.content
        holder.binding.content.atColor()
    }

    inner class FeedViewHolder(val binding: FeedLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}