package com.nightwolf.crm_test.UI.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.UI.viewModel.CollectionDetailViewModel
import com.nightwolf.crm_test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collection_detail.*


class CollectionDetailActivity : BaseActivity<CollectionDetailViewModel>() {

    companion object {
        fun jumpToCollectionDetail(context: Context, recordId: Long): Intent {
            val intent = Intent(context, CollectionDetailActivity::class.java)
            intent.putExtra("recordBeanId", recordId)
            return intent
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_collection_detail
    }

    override fun getViewModelClass(): Class<CollectionDetailViewModel> {
        return CollectionDetailViewModel::class.java
    }

    override fun initView() {
        removeButton.setOnClickListener {
            baseViewModel.updateRecord()
        }
    }

    override fun initData() {
        baseViewModel.recordBeanId = intent.getLongExtra("recordBeanId", 0)

        baseViewModel.findRecordById()
    }

    override fun startObserve() {
        super.startObserve()
        baseViewModel.recordRoomBeanLiveData.observe(this, Observer {
            text1_content.text = it.content
            text2_content.text = it.workContent
        })

        baseViewModel.dealLiveData.observe(this, Observer {
            if (it) {
                removeButton.visibility = View.GONE
                Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show()
            }
        })
    }

}