package com.example.crm_test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crm_test.bean.PostMesBean
import com.example.crm_test.room.RecordDatabase
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_collection_record.*
import java.lang.StringBuilder


/**
 *  收集内容
 */
class CollectionRecord : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection_record, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Observable.just(1)
            .subscribeOn(Schedulers.newThread())
            .flatMap {
                Observable.just(RecordDatabase.recordDb!!.recordDao.findAllRecord())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val stringBuilder = StringBuilder()
                it.forEach { k ->
                    stringBuilder.append(k)
                }
                temp.text = stringBuilder.toString()
            }, {
                Log.d("liwu", it.message)
            }
            )
    }
}
