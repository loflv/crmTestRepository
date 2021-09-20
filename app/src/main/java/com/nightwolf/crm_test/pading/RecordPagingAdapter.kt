package com.nightwolf.crm_test.pading

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nightwolf.crm_test.bean.PostMesList
import com.nightwolf.crm_test.databinding.RecordListItemLayoutBinding
import com.orhanobut.logger.Logger

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

    /**
     * 是否查看已读
     */
    var seeHaveRead = false
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecordPagingAdapter.MyViewHolder) {
            val item = getItem(position)
            //过滤 提交 和 已读的
            if (!(item?.content!!.contains("提交"))) {
                val layoutParams = holder.binding.viewLineLayout.layoutParams
                layoutParams.height = 0
                holder.binding.viewLineLayout.layoutParams = layoutParams
                return
            } else {
                //未读的隐藏 在不查看的情况下
                if (item.status == 1 && !seeHaveRead) {
                    val layoutParams = holder.binding.viewLineLayout.layoutParams
                    layoutParams.height = 0
                    holder.binding.viewLineLayout.layoutParams = layoutParams
                } else {
                    val layoutParams = holder.binding.viewLineLayout.layoutParams
                    layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                    holder.binding.viewLineLayout.layoutParams = layoutParams
                }
            }

            holder.binding.name.text = item.user?.name
            holder.binding.content.text = item?.content
            holder.binding.viewLineLayout.setOnClickListener {
                Logger.d("点击了$position")
                callback(position, item!!.operate, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            RecordListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class MyViewHolder(val binding: RecordListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}