package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginSignUpViewModel : ViewModel() {

     var isLogin: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isLogin.value = true
    }
    fun onLoginClick() {
        isLogin.value = true
    }

    fun onSignUpClick() {
        isLogin.value = false
    }


}