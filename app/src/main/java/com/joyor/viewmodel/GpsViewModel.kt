package com.joyor.viewmodel

import android.view.View

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GpsViewModel : ViewModel() {

    var isStartClick: MutableLiveData<Boolean> = MutableLiveData()
    var isYlClick: MutableLiveData<Boolean> = MutableLiveData()
    var isDlClick: MutableLiveData<Boolean> = MutableLiveData()
    var ylName: MutableLiveData<String> = MutableLiveData()
    var dlName: MutableLiveData<String> = MutableLiveData()


    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    fun onStartClick() {
        isStartClick.value = true
    }

    fun onBackClick() {
        isBack.value = true
    }

    fun onYourLocationClick() {
        isYlClick.value = true
    }

    fun onDestinationClick() {
        isDlClick.value = true
    }
}