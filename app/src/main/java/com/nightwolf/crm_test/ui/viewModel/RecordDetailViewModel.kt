package com.nightwolf.crm_test.ui.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.nightwolf.crm_test.MyApplication
import com.nightwolf.crm_test.api.CrmApi
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.base.OtherReply
import com.nightwolf.crm_test.bean.PostMesBean
import com.nightwolf.crm_test.room.RecordDatabase
import com.nightwolf.crm_test.room.RecordRoomBean
import com.nightwolf.crm_test.util.NetWorkUtils
import com.nightwolf.crm_test.util.ioThread
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RecordDetailViewModel : BaseViewModel() {
    lateinit var recordId: String
    lateinit var userId: String
    lateinit var text1_content: String
    lateinit var text2_content: String

    var reReplyList = mutableListOf<OtherReply.BodyBean.CommentsBean>()
        private set

    var postReplyListSize: MutableLiveData<Int> =
        MutableLiveData()
        private set

    init {
        postReplyListSize.value = 0
    }

    var postMesBeanLiveData: MutableLiveData<PostMesBean> = MutableLiveData()

    var sendSuccess: MutableLiveData<Int> = MutableLiveData()

    init {
        sendSuccess.value = 0
    }

    fun sendHaveRead() {
        launch {
            val phoneRetrofitService = NetWorkUtils.phoneRetrofitService(CrmApi::class.java)
            val sendHaveRead = phoneRetrofitService.sendHaveRead(
                recordId,
                "601",
                userId,
                "2010.7",
                "已阅".toRequestBody("text/plain".toMediaType()),
                "1".toRequestBody("text/plain".toMediaType())
            )
            if (sendHaveRead.scode.equals("0")) {
                sendSuccess.value = 1
            }
            phoneRetrofitService.sendMesRead(
                "1",
                userId,
                "1213283083206995",
                "2010.7"
            )

            Toast.makeText(MyApplication.mContext, "发送成功", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 标记为已读
     */
    fun signRead() {
        launch {
            val phoneRetrofitService = NetWorkUtils.phoneRetrofitService(CrmApi::class.java)
            phoneRetrofitService.sendMesRead(
                "1",
                userId,
                "1213283083206995",
                "2010.7"
            )
            sendSuccess.value = 1
            Toast.makeText(MyApplication.mContext, "发送成功", Toast.LENGTH_SHORT).show()
        }
    }

    fun initData() {
        launch {
            val recordDetail =
                NetWorkUtils.phoneRetrofitService(CrmApi::class.java).getRecordDetail(
                    recordId,
                    userId, "2010.7"
                )
            text1_content = recordDetail.body?.report?.content!!.replace("\r", "\n")
            text2_content = recordDetail.body?.report?.plan!!.replace("\r", "\n")
            postMesBeanLiveData.value = recordDetail


            //查询其他人的记录
            val otherReply = NetWorkUtils.phoneRetrofitService(CrmApi::class.java).getOtherReply(
                recordId, "601", userId, "2010.7"
            )
            otherReply.body?.comments?.let {
                reReplyList.addAll(it)
                postReplyListSize.value = reReplyList.size
            }

        }
    }

    fun insertDatabase(recordRoomBean: RecordRoomBean) {
        ioThread {
            RecordDatabase.get().recordDao().insertRecord(recordRoomBean)
        }
    }
}