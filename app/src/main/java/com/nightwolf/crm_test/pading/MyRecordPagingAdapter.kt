package com.nightwolf.crm_test.pading

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.bean.MyReportBean
import java.text.SimpleDateFormat

class MyRecordPagingAdapter(val callback: (Int, Long, MyReportBean.BodyBean.ReportsBean) -> Unit) :
    PagingDataAdapter<MyReportBean.BodyBean.ReportsBean,
            RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<MyReportBean.BodyBean.ReportsBean>() {
        override fun areItemsTheSame(
            oldItem: MyReportBean.BodyBean.ReportsBean,
            newItem: MyReportBean.BodyBean.ReportsBean
        ): Boolean {
            return oldItem.id === newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MyReportBean.BodyBean.ReportsBean,
            newItem: MyReportBean.BodyBean.ReportsBean
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    lateinit var userName: String

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyRecordPagingAdapter.MyViewHolder) {
            val item = getItem(position)


            holder.mName.text =
                SimpleDateFormat("${userName}[MM/dd日报]").format(item!!.date)
            holder.mContent.setOnClickListener {
//                Logger.d("点击了$position")
//                callback(position, item!!.operate, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        userName = MyApplication.mContext.getSharedPreferences("login", Context.MODE_PRIVATE)
            .getString("accountName", "")!!
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