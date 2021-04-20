package com.nightwolf.crm_test.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.room.RecordDatabase
import com.nightwolf.crm_test.room.RecordRoomBean
import kotlinx.coroutines.launch

class CollectionRecordViewModel : BaseViewModel() {

    var listData = MutableLiveData<MutableList<RecordRoomBean>>()
    val listDataSize: MutableLiveData<Int> = MutableLiveData()

    /**
     * dao 必须是一个才能刷新
     */
    private val dao = RecordDatabase.get().recordDao()

    init {
        listData.value = mutableListOf()
    }

    fun getList(): MutableList<RecordRoomBean> {
        return listData.value!!
    }

    fun remove(room: RecordRoomBean) = viewModelScope.launch {
        dao.delete(room)
    }

    val allCheeses = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        )
    ) {
        dao.findAllRecord()
    }.flow

}