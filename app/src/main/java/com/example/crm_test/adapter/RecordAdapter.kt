package com.example.crm_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.crm_test.R
import com.example.crm_test.UI.viewModel.RecordFragmentViewModel
import com.example.crm_test.adapter.RecordAdapter.MyViewHolder
import com.example.crm_test.bean.PostMesList
import java.text.SimpleDateFormat

class RecordAdapter(
    var mList: List<PostMesList.BodyBean.NoticesBean>,
    val loadingMore: () -> Unit,
    val callback: (Int, Long, Long) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if (viewType == FOOT_TYPE) {
            val inflate =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.record_list_foot_item_layout, parent, false)
            return FootViewHolder(inflate)
        } else {
            val inflate =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.record_list_item_layout, parent, false)
            return MyViewHolder(inflate)
        }

    }

    companion object {
        const val FOOT_TYPE = 1
        const val NORMAL_TYPE = 0
    }

    var footViewHolder: FootViewHolder? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (holder is MyViewHolder) {
            holder.mName.text =
                SimpleDateFormat("MM/dd HH:mm :  ").format(mList[position].created) + mList[position].user!!.name + "提交了日志"
            holder.mContent.setOnClickListener {
                callback(position, mList[position].operate, mList[position].id)
            }
        } else if (holder is FootViewHolder) {

            footViewHolder = holder
            if (mList.isNotEmpty()) {
                loadingMore()
            }
        }

    }

    fun hideFootViewHolder(){
        footViewHolder?.view?.visibility = GONE
    }

    override fun getItemCount(): Int {
        return mList.size + 1
    }

    override fun getItemViewType(position: Int): Int {

        if (position == mList.size) {
            return FOOT_TYPE
        } else {
            return NORMAL_TYPE
        }
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


    inner class FootViewHolder(itemView: View) :
        ViewHolder(itemView) {
        val view: View

        init {
            view = itemView.findViewById(R.id.foot_view)
        }
    }
}