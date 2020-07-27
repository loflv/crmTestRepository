package com.example.crm_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crm_test.R
import com.example.crm_test.base.OtherReply
import com.example.crm_test.databinding.RecyclterItemOtherReplayBinding
import java.text.SimpleDateFormat

class OtherReplayAdapter(val list: MutableList<OtherReply.BodyBean.CommentsBean>) :
    RecyclerView.Adapter<OtherReplayAdapter.OtherReplyAdapter>() {

    class OtherReplyAdapter(val binding: RecyclterItemOtherReplayBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherReplyAdapter {
        val inflate = DataBindingUtil.inflate<RecyclterItemOtherReplayBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recyclter_item_other_replay,
            parent,
            false
        )
        return OtherReplyAdapter(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OtherReplyAdapter, position: Int) {

        //干掉已阅
        if (list[position].content.equals("已阅") && position > 1) {
            return
        }

        holder.binding.time.text = SimpleDateFormat("HH:mm:ss").format(list[position].date)
        holder.binding.userName.text = list[position].user?.name
        holder.binding.content.text = list[position].content
//           Glide.with(holder.binding.imageView.context).load(list[position].)

    }
}
