package com.brian.kotlinkoinmvvmcoroutines.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel() : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    val showRefreshing = MutableLiveData<Boolean>()

    protected fun onIO(block: suspend (scope: CoroutineScope) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            showRefreshing.postValue(true)
            block(this)
            showRefreshing.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
