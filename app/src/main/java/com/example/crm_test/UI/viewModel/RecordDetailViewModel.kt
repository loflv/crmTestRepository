package com.example.crm_test.UI.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.crm_test.MyApplication
import com.example.crm_test.api.CrmApi
import com.example.crm_test.base.BaseViewModel
import com.example.crm_test.bean.PostMesBean
import com.example.crm_test.room.RecordDatabase
import com.example.crm_test.room.RecordRoomBean
import com.example.crm_test.util.NetWorkUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RecordDetailViewModel : BaseViewModel() {
    lateinit var passport_id: String
    lateinit var id: String
    lateinit var text1_content: String
    lateinit var text2_content: String

     var postMesBeanLiveData: MutableLiveData<PostMesBean> = MutableLiveData()

    var sendSuccess: MutableLiveData<Int> = MutableLiveData()

    init {
        sendSuccess.value = 0
    }

    fun sendHaveRead() {
        launch {
            val phoneRetrofitService = NetWorkUtils.phoneRetrofitService(CrmApi::class.java)
            val sendHaveRead = phoneRetrofitService.sendHaveRead(
                passport_id,
                "601",
                id,
                "2006.2",
                "已阅".toRequestBody("text/plain".toMediaType()),
                "1".toRequestBody("text/plain".toMediaType())
            )
            if (sendHaveRead.scode.equals("0")) {
                sendSuccess.value = 1
            }
            phoneRetrofitService.sendMesRead(
                "1",
                passport_id,
                "1213283083206995",
                "2006.2"
            )

            Toast.makeText(MyApplication.mContext, "发送成功", Toast.LENGTH_SHORT).show()
        }
    }

    fun initData() {
        launch {
            val recordDetail =
                NetWorkUtils.phoneRetrofitService(CrmApi::class.java).getRecordDetail(
                    passport_id,
                    id, "2006.2"
                )
            text1_content = recordDetail.body?.report?.content!!.replace("\r", "\n")
            text2_content = recordDetail.body?.report?.plan!!.replace("\r", "\n")
            postMesBeanLiveData.value = recordDetail
        }
    }

    fun insertDatabase(recordRoomBean: RecordRoomBean) {
        launch {
            RecordDatabase.recordDb!!.recordDao.insertRecord(recordRoomBean)
        }
    }
}