package com.example.crm_test

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crm_test.room.RecordDatabase
import com.example.crm_test.room.RecordRoomBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_collection_detail.*


class CollectionDetailActivity : AppCompatActivity() {
    lateinit var recordRoomBean: RecordRoomBean
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_detail)
        val longExtra = intent.getLongExtra("recordBeanId", 0)
        val subscribe = Observable.just(longExtra)
            .subscribeOn(Schedulers.newThread())
            .flatMap {
                Observable.just(RecordDatabase.recordDb!!.recordDao.findRecordById(longExtra))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                text1_content.text = it.content
                text2_content.text = it.workContent
                recordRoomBean = it
            }

        removeButton.setOnClickListener {
            recordRoomBean.readAble = false
            Thread {
                RecordDatabase.recordDb!!.recordDao.updateRecord(recordRoomBean)
            }.start()
        }
    }


    companion object {
        fun jumpToCollectionDetail(context: Context, recordId: Long): Intent {
            val intent = Intent(context, CollectionDetailActivity::class.java)
            intent.putExtra("recordBeanId", recordId)
            return intent
        }
    }
}