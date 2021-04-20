package com.nightwolf.crm_test.UI.viewModel

import androidx.lifecycle.MutableLiveData
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.room.RecordDatabase
import com.nightwolf.crm_test.room.RecordRoomBean

class CollectionDetailViewModel : BaseViewModel() {
    var recordBeanId: Long = 0
    lateinit var text1_content: String
    lateinit var text2_content: String

    var recordRoomBeanLiveData: MutableLiveData<RecordRoomBean> = MutableLiveData()

    var dealLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        dealLiveData.value = false
    }

    fun findRecordById() {
        launch {
            val bean = RecordDatabase.get().recordDao().findRecordById(recordBeanId)
            text1_content = bean.content
            text2_content = bean.workContent
            recordRoomBeanLiveData.value = bean
        }
    }

    fun updateRecord() {
        if (recordRoomBeanLiveData.value == null) {
            return
        }

        launch {
            RecordDatabase.get().recordDao().delete(recordRoomBeanLiveData.value!!)
            dealLiveData.value = true
        }
    }
}