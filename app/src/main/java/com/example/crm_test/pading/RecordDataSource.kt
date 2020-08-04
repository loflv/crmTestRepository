package com.example.crm_test.pading

import androidx.paging.PagingSource
import com.example.crm_test.api.CrmApi
import com.example.crm_test.bean.PostMesList
import com.example.crm_test.util.NetWorkUtils

class RecordDataSource(val userId: String) :
    PagingSource<Long, PostMesList.BodyBean.NoticesBean>() {


    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, PostMesList.BodyBean.NoticesBean> {
        val retrofitService = NetWorkUtils.phoneRetrofitService(CrmApi::class.java)
        val key: Long? = if (params.key === 0L) {
            null
        } else {
            params.key
        }
        val record = retrofitService.getRecord(userId, key, "2006.2")

        return LoadResult.Page(
            //需要加载的数据
            data = record.body!!.notices!!/*.filter {
                it.content!!.contains("提交")
                        && it.status == 0
            }*/,
            //如果可以往上加载更多就设置该参数，否则不设置
            prevKey = null,
            //加载下一页的key 如果传null就说明到底了
            nextKey = record.body?.notices?.lastOrNull()?.id
        )
    }

}