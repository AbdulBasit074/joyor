package com.joyor.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joyor.model.User

class HomeViewModel : ViewModel() {

    var isProfileClick: MutableLiveData<Boolean> = MutableLiveData()
    var isBackClick: MutableLiveData<Boolean> = MutableLiveData()
    var isLoginClick: MutableLiveData<Boolean> = MutableLiveData()
    var isGpsClick: MutableLiveData<Boolean> = MutableLiveData()
    var isUserLogin: Boolean = false
    var user: MutableLiveData<User> = MutableLiveData()


    fun onProfileClick(view: View) {
        isProfileClick.value = true
    }

    fun onBackClick(view: View) {
        isBackClick.value = true
    }

    fun onLoginClick(view: View) {
        isLoginClick.value = true
    }

    fun onGpsClick(view: View) {
        isGpsClick.value = true
    }
}