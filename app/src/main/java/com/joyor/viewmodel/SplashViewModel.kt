package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Address
import com.joyor.model.Setting
import com.joyor.model.Store
import com.joyor.model.User
import com.joyor.model.room.JoyorDb
import com.joyor.service.Results
import com.joyor.service.auth.AuthService
import com.joyor.service.setting.SettingService

class SplashViewModel : ViewModel(), Results {


    var showToast: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var userDetailRequest: Int = 1322
    var userUpateAddressRequest: Int = 222
    lateinit var context: Context
    var updateUser: Boolean = false
    private fun getUserUpdateDetail() {
        AuthService(userDetailRequest, this).userDetail(user.value?.iD!!)
    }

    fun getUserUpdateAddress() {
        AuthService(userUpateAddressRequest, this).getUserAddress(user.value?.iD!!)
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            userDetailRequest -> {
                updateUser = true
                user.value = Gson().fromJson(data, User::class.java)
            }
            userUpateAddressRequest -> {
                val userAddressList: ArrayList<Address> = Gson().fromJson(data, object : TypeToken<ArrayList<Address>>() {}.type)
                JoyorDb.newInstance(context).addressDao().deleteAddress()
                JoyorDb.newInstance(context).addressDao().addAddress(userAddressList[0])
                getUserUpdateDetail()
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }
}