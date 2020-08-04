package com.example.crm_test.pading

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crm_test.R
import com.example.crm_test.bean.PostMesList
import java.text.SimpleDateFormat

class PagingAdapter(val callback: (Int, Long, Long) -> Unit) :
    PagingDataAdapter<PostMesList.BodyBean.NoticesBean,
            RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<PostMesList.BodyBean.NoticesBean>() {
        override fun areItemsTheSame(
            oldItem: PostMesList.BodyBean.NoticesBean,
            newItem: PostMesList.BodyBean.NoticesBean
        ): Boolean {
            return oldItem.belongId === newItem.belongId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: PostMesList.BodyBean.NoticesBean,
            newItem: PostMesList.BodyBean.NoticesBean
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PagingAdapter.MyViewHolder) {
            val item = getItem(position)
            holder.mName.text =
                SimpleDateFormat("MM/dd HH:mm :  ").format(item?.created) + item?.user!!.name + "提交了日志"
            holder.mContent.setOnClickListener {
                callback(position, item.operate, item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.record_list_item_layout, parent, false)
        return MyViewHolder(inflate)
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var mName: TextView
        var mContent: View

        init {
            mName = itemView.findViewById(R.id.name)
            mContent = itemView.findViewById(R.id.content)
        }
    }
}