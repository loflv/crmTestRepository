package com.example.crm_test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crm_test.CollectionDetailActivity
import com.example.crm_test.R
import com.example.crm_test.databinding.LayoutCollectionItemBinding
import com.example.crm_test.room.RecordRoomBean

class ColleactionAdatper(val content: Context, val listLiveDate: MutableList<RecordRoomBean>) :
    RecyclerView.Adapter<ColleactionAdatper.ColleactionViewHolder>() {


    inner class ColleactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var bindingUtil: LayoutCollectionItemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColleactionViewHolder {

        val binding = DataBindingUtil.inflate<LayoutCollectionItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_collection_item,
            parent, false
        )
        val colleactionViewHolder = ColleactionViewHolder(binding.root)
        colleactionViewHolder.bindingUtil = binding

        return colleactionViewHolder
    }

    override fun getItemCount(): Int {
        return listLiveDate.size
    }

    override fun onBindViewHolder(holder: ColleactionViewHolder, position: Int) {
        holder.bindingUtil.recordName.text = "${listLiveDate[position].name}的--"
        holder.bindingUtil.titleContent.text = listLiveDate[position].title
        holder.bindingUtil.container.setOnClickListener {
            content.startActivity(
                CollectionDetailActivity.jumpToCollectionDetail(
                    content,
                    listLiveDate[position].id
                )
            )

        }
    }
}