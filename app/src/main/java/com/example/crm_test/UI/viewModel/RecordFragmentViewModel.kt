package com.example.crm_test.UI.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.crm_test.api.CrmApi
import com.example.crm_test.base.BaseViewModel
import com.example.crm_test.bean.PostMesList
import com.example.crm_test.util.NetWorkUtils

class RecordFragmentViewModel : BaseViewModel() {

    /**
     * 最后一个记录的id
     */
    var lastRecordId: Long? = null
    var number = 0

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

    fun loadMes(userId: String) {
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
                number++
                loadMes(userId)
                return@launch
            }

            number = 0

            //过滤重复提交
//            val list: MutableList<PostMesList.BodyBean.NoticesBean> = mutableListOf()
//            filter?.forEach { mes ->
//                if (list.all { contont -> contont.operate != mes.operate }) {
//                    list.add(mes)
//                }
//            }
            addList(filter!!)
        }


    }
}