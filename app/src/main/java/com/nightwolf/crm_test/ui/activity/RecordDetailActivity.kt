package com.nightwolf.crm_test.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightwolf.crm_test.R
import com.nightwolf.crm_test.adapter.OtherReplayAdapter
import com.nightwolf.crm_test.base.BaseActivity
import com.nightwolf.crm_test.databinding.ActivityRecordDetailBinding
import com.nightwolf.crm_test.room.RecordRoomBean
import com.nightwolf.crm_test.ui.viewModel.RecordDetailViewModel
import com.nightwolf.crm_test.util.ioThread


/**
 * 日志具体内容
 */
class RecordDetailActivity : BaseActivity<RecordDetailViewModel>() {

    companion object {
        fun jumpToRecordDetail(context: Context, recordId: Long, id: Long): Intent {
            val intent = Intent(context, RecordDetailActivity::class.java)
            intent.putExtra("passport_id", recordId)
            intent.putExtra("id", id)
            return intent
        }
    }

    override fun onBackPressed() {
        setResult(baseViewModel.sendSuccess.value!!)
        finish()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_record_detail
    }

    override fun getViewModelClass(): Class<RecordDetailViewModel> {
        return RecordDetailViewModel::class.java
    }

    lateinit var binding: ActivityRecordDetailBinding
    override fun initView() {
        binding = ActivityRecordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.read.setOnClickListener {
            binding.read.isClickable = false
            baseViewModel.sendHaveRead()
            binding.read.isClickable = true
        }

        binding.collect.setOnClickListener {

            if (baseViewModel.postMesBeanLiveData.value == null) {
                return@setOnClickListener
            }
            val recordRoomBean = RecordRoomBean(
                0,
                baseViewModel.postMesBeanLiveData.value!!.body?.report?.user?.name!!,
                "日报",
                binding.text1Content.text.toString(),
                binding.text2Content.text.toString()
            )
            ioThread {
                baseViewModel.insertDatabase(recordRoomBean)
            }
        }

        binding.signRead.setOnClickListener {
            baseViewModel.signRead()
        }

    }

    override fun initData() {
        baseViewModel.passport_id = intent.getLongExtra("passport_id", 0).toString()
        baseViewModel.id = intent.getLongExtra("id", 0).toString()
        baseViewModel.initData()

        binding.otherReplyRecycler.layoutManager = LinearLayoutManager(this)
        binding.otherReplyRecycler.adapter =
            OtherReplayAdapter(baseViewModel.reReplyList, baseViewModel.id)
    }

    override fun startObserve() {
        super.startObserve()
        baseViewModel.sendSuccess.observe(this, Observer {
            if (it == 1) {
                binding.read.visibility = View.GONE
            }
        })

        baseViewModel.postMesBeanLiveData.observe(this, Observer {
            binding.text1Content.text = baseViewModel.text1_content
            binding.text2Content.text = baseViewModel.text2_content
        })

        baseViewModel.postReplyListSize.observe(this, Observer {
            binding.otherReplyRecycler.adapter!!.notifyDataSetChanged()
        })

    }
}