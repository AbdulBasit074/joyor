package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Setting
import com.joyor.model.Store
import com.joyor.model.User
import com.joyor.service.Results
import com.joyor.service.auth.AuthService
import com.joyor.service.setting.SettingService

class SplashViewModel : ViewModel(), Results {


    var showToast: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var userDetailRequest: Int = 1322
    var updateUser: Boolean = false
    fun getUserUpdateDetail() {
        AuthService(userDetailRequest, this).userDetail(user.value?.iD!!)
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (userDetailRequest) {
            userDetailRequest -> {
                updateUser = true
                user.value = Gson().fromJson(data, User::class.java)
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }
}