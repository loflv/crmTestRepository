package com.example.crm_test.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<Exception>()

    fun <T> launch(block: suspend (CoroutineScope.() -> T)) {
        viewModelScope.launch {
            tryCatch(block)
        }
    }

    private fun <T> tryCatch(block: suspend (CoroutineScope.() -> T)) {
        try {
            viewModelScope.launch {
                block()
            }
        } catch (e: Exception) {
            errorLiveData.value = e
        } 
    }

}