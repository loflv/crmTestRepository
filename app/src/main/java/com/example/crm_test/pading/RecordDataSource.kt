package com.example.crm_test.pading

import androidx.paging.PagingSource
import com.example.crm_test.api.CrmApi
import com.example.crm_test.bean.PostMesList
import com.example.crm_test.util.NetWorkUtils
import com.orhanobut.logger.Logger

class RecordDataSource(val userId: String) :
    PagingSource<Long, PostMesList.BodyBean.NoticesBean>() {

    var emptyTime = 0

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, PostMesList.BodyBean.NoticesBean> {
        val retrofitService = NetWorkUtils.phoneRetrofitService(CrmApi::class.java)
        var key: Long? = if (0L === params.key) {
            null
        } else {
            params.key
        }


        val record = retrofitService.getRecord(userId, key, "2010.7")

        return try {
            LoadResult.Page(
                //需要加载的数据
                data = record.body!!.notices!!
                    .filterIndexed { i, bean ->
                        // if (value.data.isEmpty()) NotLoading.Complete else NotLoading.Incomplete
                        //好蠢
                        i == 1 || (bean.content!!.contains("提交")
                                && bean.status == 0)

                    }
                    .apply {
                        if (this.size < 2) {
                            emptyTime++
                        } else {
                            emptyTime = 0
                        }
                    },
                //如果可以往上加载更多就设置该参数，否则不设置
                prevKey = null,
                //加载下一页的key 如果传null就说明到底了
                nextKey =
                if (emptyTime > 5) {
                    null
                } else {
                    record.body?.notices?.lastOrNull()?.id
                }
            )
        } catch (e: Exception) {
            Logger.e(e.message.toString())
            LoadResult.Error(e)
        }
    }

}