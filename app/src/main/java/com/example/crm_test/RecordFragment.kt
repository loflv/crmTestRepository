package com.example.crm_test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crm_test.api.CrmApi
import com.example.crm_test.bean.PostMesList
import com.example.crm_test.util.NetWorkUtils
import com.example.crm_test.adapter.RecordAdapter
import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_record.*

/**
 * 其他人提交的记录
 * 会被返回创建头疼疼
 */
class RecordFragment : Fragment() {

    var lastRecordId: Long? = null
    var number: Int = 0
    var layoutManager: LinearLayoutManager? = null
    lateinit var recyclerView: RecyclerView

    lateinit var recordAdapter: RecordAdapter
    lateinit var listOf: MutableList<PostMesList.BodyBean.NoticesBean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listOf = mutableListOf()
        initData()
        recordAdapter =
            RecordAdapter(listOf) { i, recordId, id ->
                val intent =
                    RecordDetailActivity.jumpToRecordDetail(requireActivity(), recordId, id)
                startActivityForResult(intent, i)

            }

        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        my_recycler.adapter = recordAdapter
        recyclerView = my_recycler
        my_recycler.layoutManager = LinearLayoutManager(requireActivity())
        layoutManager = my_recycler.layoutManager as LinearLayoutManager
        my_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager?.itemCount ==
                    (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                ) {
                    initData()
                }
            }
        })

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.setRefreshing(false)
        }

    }

    /**
     * 移除已读记录
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            return
        }

        listOf.removeAt(requestCode)
        recyclerView.adapter?.notifyItemRemoved(requestCode)
        recyclerView.adapter?.notifyItemRangeChanged(requestCode, listOf.size)
    }

    private fun initData() {

        if (number > 3) {
            return
        }

        val retrofitService = NetWorkUtils.phoneRetrofitService(CrmApi::class.java)

        val userId = MyApplication.mContext.getSharedPreferences(
            "default",
            Context.MODE_PRIVATE
        ).getLong("passport_id", 0)

        retrofitService.getRecord(userId.toString(), lastRecordId, "2006.2")
            .subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe(object : Observer<PostMesList> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: PostMesList) {

                    var hasMore = t.body?.hasMore
                    lastRecordId = t.body?.notices?.last()?.id

                    //过滤出已读提交的数据
                    val filter = t.body?.notices?.filter {
                        it.content!!.contains("提交")
//                                && it.status == 0
                    }

                    if (hasMore == null) {
                        hasMore = false
                    }

                    if (filter?.size == 0 && hasMore) {
                        number++
                        initData()
                        return
                    }

                    number = 0

                    //过滤重复提交
                    val list: MutableList<PostMesList.BodyBean.NoticesBean> = mutableListOf()
                    filter?.forEach { mes ->

                        if (list.all { it.operate != mes.operate }) {
                            list.add(mes)
                        }
                    }

                    listOf.addAll(list)
                    recordAdapter.notifyDataSetChanged()

                    recyclerView.post {
                        if (layoutManager?.itemCount ==
                            (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1
                        ) {
                            initData()
                        }
                    }

                }

                override fun onError(e: Throwable) {
                    Logger.e(e.message.toString())
                    Toast.makeText(requireActivity(), "请先登录", Toast.LENGTH_SHORT).show()
                }
            })
    }
}


