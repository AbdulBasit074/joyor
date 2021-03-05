package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RouteViewModel : ViewModel() {

    var back: MutableLiveData<Boolean> = MutableLiveData()


    fun isBackClick() {
        back.value = true
    }

}