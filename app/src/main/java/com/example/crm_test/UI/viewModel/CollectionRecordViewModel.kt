package com.example.crm_test.UI.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.crm_test.base.BaseViewModel
import com.example.crm_test.room.RecordDatabase
import com.example.crm_test.room.RecordRoomBean
import kotlinx.coroutines.launch

class CollectionRecordViewModel : BaseViewModel() {

    var listData = MutableLiveData<MutableList<RecordRoomBean>>()
    val listDataSize: MutableLiveData<Int> = MutableLiveData()

    init {
        listData.value = mutableListOf()
    }

    fun getList(): MutableList<RecordRoomBean> {
        return listData.value!!
    }

    fun remove(room: RecordRoomBean) = viewModelScope.launch {
        RecordDatabase.get().recordDao().delete(room)
    }

    val allCheeses = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        )
    ) {
        RecordDatabase.get().recordDao().findAllRecord()
    }.flow

}