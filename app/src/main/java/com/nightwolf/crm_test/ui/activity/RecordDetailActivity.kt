package com.nightwolf.crm_test.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.adapter.OtherReplayAdapter
import com.nightwolf.crm_test.base.BaseActivity
import com.nightwolf.crm_test.databinding.ActivityRecordDetailBinding
import com.nightwolf.crm_test.ext.ioThread
import com.nightwolf.crm_test.room.RecordRoomBean
import com.nightwolf.crm_test.ui.viewModel.RecordDetailViewModel


/**
 * 日志具体内容
 */
class RecordDetailActivity : BaseActivity<RecordDetailViewModel>() {

    companion object {
        fun jumpToRecordDetail(context: Context, recordId: Long, id: Long): Intent {
            val intent = Intent(context, RecordDetailActivity::class.java)
            intent.putExtra("recordId", recordId)
            intent.putExtra("userId", id)
            return intent
        }
    }

    override fun onBackPressed() {
        setResult(baseViewModel.sendSuccess.value!!)
        finish()
    }

    override fun getViewModelClass(): Class<RecordDetailViewModel> {
        return RecordDetailViewModel::class.java
    }

    lateinit var binding: ActivityRecordDetailBinding
     fun initView() {
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


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    fun initData() {
        baseViewModel.recordId = intent.getLongExtra("recordId", 0).toString()
        baseViewModel.userId = intent.getLongExtra("userId", 0).toString()
        baseViewModel.initData()

        binding.otherReplyRecycler.layoutManager = LinearLayoutManager(this)
        binding.otherReplyRecycler.adapter =
            OtherReplayAdapter(baseViewModel.reReplyList, baseViewModel.userId)

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

            baseViewModel.reReplyList.firstOrNull {
                it.user?.name == MyApplication.mContext.getSharedPreferences(
                    "login",
                    Context.MODE_PRIVATE
                )
                    .getString("accountName", "") ?: ""
            }?.let {
                binding.read.visibility = View.GONE
//                lifecycleScope.launch {
//                    baseViewModel.signRead()
//                }

            }
        })

    }


}
