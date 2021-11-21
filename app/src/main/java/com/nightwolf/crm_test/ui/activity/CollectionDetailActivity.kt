package com.nightwolf.crm_test.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nightwolf.crm_test.base.BaseActivity
import com.nightwolf.crm_test.databinding.ActivityCollectionDetailBinding
import com.nightwolf.crm_test.ui.viewModel.CollectionDetailViewModel

/**
 * 收集详情
 */
class CollectionDetailActivity : BaseActivity<CollectionDetailViewModel>() {

    lateinit var binding: ActivityCollectionDetailBinding

    companion object {
        fun jumpToCollectionDetail(context: Context, recordId: Long): Intent {
            val intent = Intent(context, CollectionDetailActivity::class.java)
            intent.putExtra("recordBeanId", recordId)
            return intent
        }
    }


    override fun getViewModelClass(): Class<CollectionDetailViewModel> {
        return CollectionDetailViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    fun initView() {
        binding = ActivityCollectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.removeButton.setOnClickListener {
            baseViewModel.updateRecord()
        }
    }

    fun initData() {
        baseViewModel.recordBeanId = intent.getLongExtra("recordBeanId", 0)

        baseViewModel.findRecordById()

        baseViewModel.recordRoomBeanLiveData.observe(this, Observer {
            binding.text1Content.text = it.content
            binding.text2Content.text = it.workContent
        })

        baseViewModel.dealLiveData.observe(this, Observer {
            if (it) {
                binding.removeButton.visibility = View.GONE
                Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show()
            }
        })
    }


}