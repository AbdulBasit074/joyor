package com.joyor.viewmodel

import android.view.View

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GpsViewModel : ViewModel() {

    var isStartClick: MutableLiveData<Boolean> = MutableLiveData()
    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    fun onStartClick(view: View) {
        isStartClick.value = true
    }
    fun onBackClick(){
        isBack.value = true
    }


}