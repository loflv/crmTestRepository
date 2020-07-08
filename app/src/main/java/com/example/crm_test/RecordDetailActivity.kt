package com.example.crm_test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crm_test.api.CrmApi
import com.example.crm_test.bean.PostMesBean
import com.example.crm_test.util.MyApplication
import com.example.crm_test.util.NetWorkUtils
import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_record_detail.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


/**
 * 日志具体内容
 */
class RecordDetailActivity : AppCompatActivity() {

    /**
     * 标志位是否发送成功
     */
    var sendSuccess = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_detail)

        val retrofitService = NetWorkUtils.phoneRetrofitService(CrmApi::class.java)
        val userId = MyApplication.mContext.getSharedPreferences(
            "default",
            Context.MODE_PRIVATE
        ).getLong("passport_id", 0)
        //发送已阅
        read.setOnClickListener {
            retrofitService.sendHaveRead(
                intent.getLongExtra("passport_id", 0).toString(),
                "601",
                userId.toString(),
                "2006.2",
                "已阅".toRequestBody("text/plain".toMediaType()),
                "1".toRequestBody("text/plain".toMediaType())
            )
                .subscribeOn(Schedulers.newThread())
                .flatMap {
                    if (it.scode.equals("0")) {
                        sendSuccess = 1
                    }
                    retrofitService.sendMesRead(
                        "1",
                        intent.getLongExtra("id", 0).toString(), "1213283083206995",
                        "2006.2"
                    )
                }
                .subscribeOn(Schedulers.newThread()).observeOn(
                    AndroidSchedulers.mainThread()
                ).subscribe(object : Observer<String> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: String) {
                        Toast.makeText(this@RecordDetailActivity, "发送成功", Toast.LENGTH_SHORT).show()
                        read.visibility = View.GONE
                    }

                    override fun onError(e: Throwable) {
                    }

                })
        }

        //查看详情
        retrofitService.getRecordDetail(
            intent.getLongExtra("passport_id", 0).toString(),
            userId.toString(), "2006.2"
        )
            .subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe(object : Observer<PostMesBean> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: PostMesBean) {
                    text1_content.text = t.body?.report?.content?.replace("\r", "\n")
                    text2_content.text = t.body?.report?.plan?.replace("\r", "\n")
                }

                override fun onError(e: Throwable) {
                    Logger.e(e.message.toString())
                }
            })
    }

    companion object {
        fun jumpToRecordDetail(context: Context, recordId: Long, id: Long): Intent {
            val intent = Intent(context, RecordDetailActivity::class.java)
            intent.putExtra("passport_id", recordId)
            intent.putExtra("id", id)
            return intent
        }
    }

    override fun onBackPressed() {
        setResult(sendSuccess)
        finish()
    }
}
