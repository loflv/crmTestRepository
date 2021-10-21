package com.nightwolf.crm_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.bean.OtherReply
import com.nightwolf.crm_test.databinding.RecyclterItemOtherReplayBinding
import java.text.SimpleDateFormat

class OtherReplayAdapter(
    val list: MutableList<OtherReply.DataBean.CommentListBean>,
    val id: String
) :
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
        if ((list[position].content?.length
                ?: 0) < 3 && position > 1 && list[position].id != id.toLong()
        ) {
            holder.binding.userName.visibility = View.GONE
            holder.binding.time.visibility = View.GONE
            holder.binding.content.visibility = View.GONE
            return
        }

        holder.binding.time.text = SimpleDateFormat("HH:mm:ss").format(list[position].createdAt)
        holder.binding.userName.text = list[position].user?.name
        holder.binding.content.text = list[position].content

    }
}
