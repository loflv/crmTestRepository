package com.example.crm_test.UI.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.example.crm_test.R
import com.example.crm_test.UI.viewModel.RecordDetailViewModel
import com.example.crm_test.base.BaseActivity
import com.example.crm_test.room.RecordRoomBean
import kotlinx.android.synthetic.main.activity_record_detail.*


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

    override fun initView() {
        read.setOnClickListener {
            read.isClickable = false
            baseViewModel.sendHaveRead()
            read.isClickable = true
        }

        collect.setOnClickListener {

            if (baseViewModel.postMesBeanLiveData.value == null) {
                return@setOnClickListener
            }
            val recordRoomBean = RecordRoomBean(
                baseViewModel.postMesBeanLiveData.value!!.body?.report?.user?.name!!,
                "日报",
                text1_content.text.toString(),
                text2_content.text.toString()
            )

            baseViewModel.insertDatabase(recordRoomBean)
        }

        signRead.setOnClickListener {
            baseViewModel.signRead()
        }

    }

    override fun initData() {
        baseViewModel.passport_id = intent.getLongExtra("passport_id", 0).toString()
        baseViewModel.id = intent.getLongExtra("id", 0).toString()
        baseViewModel.initData()
    }

    override fun startObserve() {
        super.startObserve()
        baseViewModel.sendSuccess.observe(this, Observer {
            if (it == 1) {
                read.visibility = View.GONE
            }
        })

        baseViewModel.postMesBeanLiveData.observe(this, Observer {
            text1_content.text = baseViewModel.text1_content
            text2_content.text = baseViewModel.text2_content
        })

    }
}
