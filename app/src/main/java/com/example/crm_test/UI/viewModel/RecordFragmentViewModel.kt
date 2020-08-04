package com.example.crm_test.UI.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.crm_test.base.BaseViewModel
import com.example.crm_test.bean.PostMesList
import com.example.crm_test.pading.RecordDataSource

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

    suspend fun loadMes(userId: String) = Pager(PagingConfig(20)) {
        RecordDataSource(userId)
    }.flow.cachedIn(viewModelScope)

    /*{
        val retrofitService = NetWorkUtils.phoneRetrofitService(CrmApi::class.java)

        launch {

            val t = retrofitService.getRecord(userId, lastRecordId, "2006.2")
            var hasMore = t.body?.hasMore
            lastRecordId = t.body?.notices?.last()?.id

            //过滤出已读提交的数据
            val filter = t.body?.notices?.filter {
                it.content!!.contains("提交")
                        && it.status == 0
            }

            if (hasMore == null) {
                hasMore = false
            }

            if (filter?.size == 0 && hasMore) {
                number.value = number.value!! + 1
                loadMes(userId)
                return@launch
            }

            number.value = 0

            //过滤重复提交
//            val list: MutableList<PostMesList.BodyBean.NoticesBean> = mutableListOf()
//            filter?.forEach { mes ->
//                if (list.all { contont -> contont.operate != mes.operate }) {
//                    list.add(mes)
//                }
//            }
            addList(filter!!)
        }*/


//}
}