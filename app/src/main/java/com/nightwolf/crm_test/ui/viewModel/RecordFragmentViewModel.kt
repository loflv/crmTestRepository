package com.nightwolf.crm_test.ui.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.nightwolf.crm_test.base.BaseViewModel
import com.nightwolf.crm_test.paging.RecordDataSource

class RecordFragmentViewModel : BaseViewModel() {

    fun loadMes(userId: String, unread: Int) = Pager(PagingConfig(8, initialLoadSize = 8)) {
        RecordDataSource(userId, unread)
    }.flow
        //无用
        /*.onStart {
           if (!NetWorkUtil.isConnected(MyApplication.mContext)) {
               currentCoroutineContext().cancel()
           }
           mStateLiveData.value = LoadState
       }.onCompletion {
           mStateLiveData.value = SuccessState
       }.catch {
           mStateLiveData.value = ErrorState(it.message)
       }*/.cachedIn(viewModelScope)

}