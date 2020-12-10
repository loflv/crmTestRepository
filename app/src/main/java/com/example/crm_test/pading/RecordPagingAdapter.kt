package com.example.crm_test.pading

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crm_test.R
import com.example.crm_test.bean.PostMesList
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat

class RecordPagingAdapter(val callback: (Int, Long, PostMesList.BodyBean.NoticesBean) -> Unit) :
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
        if (holder is RecordPagingAdapter.MyViewHolder) {
            val item = getItem(position)
            //过滤 提交已读的
            if (!(item?.content!!.contains("提交") && item.status == 0)) {
                val layoutParams = holder.mContent.layoutParams
                layoutParams.height = 0
                holder.mContent.layoutParams = layoutParams
                return
            } else {
                val layoutParams = holder.mContent.layoutParams
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                holder.mContent.layoutParams = layoutParams
            }

            holder.mName.text = item.user?.name + item?.content
            holder.mContent.setOnClickListener {
                Logger.d("点击了$position")
                callback(position, item!!.operate, item)
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