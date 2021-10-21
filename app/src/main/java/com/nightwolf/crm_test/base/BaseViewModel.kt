package com.nightwolf.crm_test.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nightwolf.crm_test.bean.global.ErrorState
import com.nightwolf.crm_test.bean.global.LoadState
import com.nightwolf.crm_test.bean.global.State
import com.nightwolf.crm_test.bean.global.SuccessState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<Exception>()

    val mStateLiveData = MutableLiveData<State>()

    //使用Flow流式编程类似RxJava
    fun <T> flowEx(block: suspend () -> T) = flow {
        emit(block())
    }.onStart {
        mStateLiveData.value = LoadState
    }.onCompletion {
        mStateLiveData.value = SuccessState
    }.catch { cause ->
        mStateLiveData.value = ErrorState(cause.message)
    }.asLiveData()


    fun <T> launch(block: suspend (CoroutineScope.() -> T)) {
        viewModelScope.launch {
            try {
                viewModelScope.launch {
                    block()
                }
            } catch (e: Exception) {

            }
        }
    }

}