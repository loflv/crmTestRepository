package com.nightwolf.crm_test.pading

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.bean.MyReportBean
import com.nightwolf.crm_test.databinding.RecordListItemLayoutBinding
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


            holder.binding.name.text =
                SimpleDateFormat("\n${userName}-MM月dd日的报告").format(item!!.date)
            holder.binding.viewLineLayout.setOnClickListener {
                callback(position, item.id!!, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        userName = MyApplication.mContext.getSharedPreferences("login", Context.MODE_PRIVATE)
            .getString("accountName", "")!!


        val binding = RecordListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.imgShow.setImageResource(R.mipmap.img_my_record)
        return MyViewHolder(
            binding
        )
    }


    inner class MyViewHolder(val binding: RecordListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}