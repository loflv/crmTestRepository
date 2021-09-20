package com.nightwolf.crm_test.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.bean.PostMesList
import com.nightwolf.crm_test.pading.RecordDataSource

class RecordFragmentViewModel : BaseViewModel() {

    /**
     * 最后一个记录的id
     */
    var lastRecordId: Long? = null
    var number: MutableLiveData<Int> = MutableLiveData()

    init {
        number.value = 0
    }

    var listLiveData: MutableLiveData<MutableList<PostMesList.BodyBean.NoticesBean>> =
        MutableLiveData()

    init {
        listLiveData.value = mutableListOf()
    }

    var recyclerListSize: MutableLiveData<Int> = MutableLiveData()

    init {
        recyclerListSize.value = 0
    }


    private fun addList(list: List<PostMesList.BodyBean.NoticesBean>) {
        listLiveData.value!!.addAll(list)
        recyclerListSize.value = listLiveData.value!!.size
    }

    fun getList(): MutableList<PostMesList.BodyBean.NoticesBean> {
        return listLiveData.value!!
    }

    fun loadMes(userId: String,unread:Int) = Pager(PagingConfig(3)) {
        RecordDataSource(userId,unread)
    }.flow.cachedIn(viewModelScope)

}