package com.example.crm_test.UI.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.crm_test.base.BaseViewModel
import com.example.crm_test.room.RecordDatabase
import com.example.crm_test.room.RecordRoomBean

class CollectionRecordViewModel : BaseViewModel() {

    var listData = MutableLiveData<MutableList<RecordRoomBean>>()
    val listDataSize: MutableLiveData<Int> = MutableLiveData()

    init {
        listData.value = mutableListOf()
    }

    fun getList(): MutableList<RecordRoomBean> {
        return listData.value!!
    }

    fun findAll() {
        launch {
            val findAllRecord = RecordDatabase.recordDb!!.recordDao.findAllRecord()
            listData.value!!.addAll(findAllRecord.filter { !it.readAble })
            listDataSize.value = listData.value!!.size
        }
    }
}