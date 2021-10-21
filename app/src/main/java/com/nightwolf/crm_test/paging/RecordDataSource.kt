package com.nightwolf.crm_test.paging

import androidx.paging.PagingSource
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.api.CrmApi
import com.nightwolf.crm_test.bean.PostMesList
import com.nightwolf.crm_test.bean.global.NO_NET_WORK_ERROR
import com.nightwolf.crm_test.util.NetWorkUtil
import com.nightwolf.crm_test.util.RetrofitUtils
import com.orhanobut.logger.Logger

class RecordDataSource(val userId: String, val unread: Int) :
    PagingSource<Long, PostMesList.BodyBean.NoticesBean>() {

    var emptyTime = 0

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, PostMesList.BodyBean.NoticesBean> {

        if (!NetWorkUtil.isConnected(MyApplication.mContext)) {
            return LoadResult.Error(Exception(NO_NET_WORK_ERROR))
        }

        val retrofitService = RetrofitUtils.createRetrofitService(CrmApi::class.java)
        var key = if (0L == params.key) {
            null
        } else {
            params.key
        }
        return try {
            val record = retrofitService.getRecord(userId, key, "2010.7", unread)

            LoadResult.Page(
                //需要加载的数据
                data = record.body!!.notices!!
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