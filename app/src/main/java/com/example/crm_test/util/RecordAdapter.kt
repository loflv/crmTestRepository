package com.example.crm_test.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.crm_test.R
import com.example.crm_test.bean.PostMesList
import com.example.crm_test.util.RecordAdapter.MyViewHolder

class RecordAdapter(
    var mList: List<PostMesList.BodyBean.NoticesBean>,
    val callback: (Int, Long, Long) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.record_list_item_layout, parent, false)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mName.text = mList[position].user!!.name + "提交了日志"
        holder.mContent.setOnClickListener {
            callback(position, mList[position].operate, mList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(itemView: View) :
        ViewHolder(itemView) {
        var mName: TextView
        var mContent: View

        init {
            mName = itemView.findViewById(R.id.name)
            mContent = itemView.findViewById(R.id.content)
        }
    }

}