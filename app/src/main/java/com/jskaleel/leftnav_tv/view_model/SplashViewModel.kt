package com.jskaleel.leftnav_tv.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private var _changePage: MutableLiveData<Boolean> = MutableLiveData()
    var changePage: LiveData<Boolean> = _changePage

    fun listenPageChange() {
        GlobalScope.launch {
            delay(3_000)
            _changePage.postValue(true)
        }
    }
}