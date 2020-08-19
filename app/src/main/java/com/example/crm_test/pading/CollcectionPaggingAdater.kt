package com.example.crm_test.pading

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crm_test.R
import com.example.crm_test.databinding.LayoutCollectionItemBinding
import com.example.crm_test.room.RecordRoomBean

class CollcectionPaggingAdater(val callback: (Int, Long) -> Unit) :
    PagingDataAdapter<RecordRoomBean, CollcectionPaggingAdater.ColleactionViewHolder>(object :
        DiffUtil.ItemCallback<RecordRoomBean>() {
        override fun areItemsTheSame(oldItem: RecordRoomBean, newItem: RecordRoomBean): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecordRoomBean, newItem: RecordRoomBean): Boolean {
            return oldItem.content == newItem.content
        }

    }) {
    override fun onBindViewHolder(holder: ColleactionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
        holder.bindingUtil.recordName.text = "${item?.name}的--"
        holder.bindingUtil.titleContent.text = item?.title
        holder.bindingUtil.container.setOnClickListener {
            callback(position, item!!.id)
        }
        holder.bindingUtil.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColleactionViewHolder {
        val inflate = DataBindingUtil.inflate<LayoutCollectionItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_collection_item,
            parent,
            false
        )
        return ColleactionViewHolder(inflate)
    }

    class ColleactionViewHolder(val bindingUtil: LayoutCollectionItemBinding) :
        RecyclerView.ViewHolder(bindingUtil.root) {
        var mRoom: RecordRoomBean? = null
        fun bindTo(room: RecordRoomBean?) {
            mRoom = room
        }
    }
}