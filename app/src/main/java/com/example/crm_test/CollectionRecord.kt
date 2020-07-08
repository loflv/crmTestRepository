package com.example.crm_test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crm_test.adapter.ColleactionAdatper
import com.example.crm_test.room.RecordDatabase
import com.example.crm_test.room.RecordRoomBean
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_collection_record.*


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

        record_recycler.layoutManager = LinearLayoutManager(requireContext())
        val mutableListOf = mutableListOf<RecordRoomBean>()
        val colleactionAdatper = ColleactionAdatper(requireActivity(), mutableListOf)
        record_recycler.adapter = colleactionAdatper

        val subscribe = Observable.just(1)
            .subscribeOn(Schedulers.newThread())
            .flatMap {
                Observable.just(RecordDatabase.recordDb!!.recordDao.findAllRecord())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableListOf.addAll(it)

                colleactionAdatper.notifyDataSetChanged()
            }, {
                Log.d("liwu", it.message)
            }
            )
    }
}
