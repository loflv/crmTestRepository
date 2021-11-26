package com.nightwolf.crm_test.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nightwolf.crm_test.api.CrmApi
import com.nightwolf.crm_test.bean.MyReportBean
import com.nightwolf.crm_test.util.RetrofitUtils
import com.orhanobut.logger.Logger

class MyRecordDataSource(val userId: String) :
    PagingSource<Int, MyReportBean.BodyBean.ReportsBean>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyReportBean.BodyBean.ReportsBean> {
        val retrofitService = RetrofitUtils.createRetrofitService(CrmApi::class.java)

        var key = if (params.key == null) {
            1
        } else {
            params.key
        }

        val record = retrofitService.getMyRecord(
            userId,
            "2010.7",
            "desc",
            "created_at",
            params.loadSize,
            key!!
        )

        return try {
            LoadResult.Page(
                //需要加载的数据
                data = record.body!!.reports!!,
                //如果可以往上加载更多就设置该参数，否则不设置
                prevKey = null,
                //加载下一页的key 如果传null就说明到底了
                nextKey = if (key * 20 < record.body!!.count) {
                    key + 1
                } else {
                    null
                }
            )
        } catch (e: Exception) {
            Logger.e(e.message.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MyReportBean.BodyBean.ReportsBean>): Int? {
        return null
    }

}