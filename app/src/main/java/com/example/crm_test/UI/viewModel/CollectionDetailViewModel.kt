package com.example.crm_test.UI.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.crm_test.base.BaseViewModel
import com.example.crm_test.room.RecordDatabase
import com.example.crm_test.room.RecordRoomBean

class CollectionDetailViewModel : BaseViewModel() {
    var recordBeanId: Long = 0
    lateinit var text1_content: String
    lateinit var text2_content: String

    var recordRoomBeanLiveData: MutableLiveData<RecordRoomBean> = MutableLiveData()

    var readLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        readLiveData.value = false
    }

    fun findRecordById() {
        launch {
            val bean = RecordDatabase.recordDb!!.recordDao.findRecordById(recordBeanId)
            text1_content = bean.content
            text2_content = bean.workContent
            recordRoomBeanLiveData.value = bean
        }
    }

    fun updateRecord() {
        if (recordRoomBeanLiveData.value == null) {
            return
        }

        recordRoomBeanLiveData.value!!.readAble = true

        launch {
            RecordDatabase.recordDb!!.recordDao.updateRecord(recordRoomBeanLiveData.value!!)
            readLiveData.value = true
        }
    }
}