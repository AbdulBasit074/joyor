package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.helper.Constants
import com.joyor.helper.Persister
import com.joyor.model.*
import com.joyor.model.room.JoyorDb
import com.joyor.service.Results
import com.joyor.service.auth.AuthService
import com.joyor.service.register.RegisterService
import com.joyor.service.setting.SettingService

class SplashViewModel : ViewModel(), Results {


    var showToast: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    private val userDetailRequest: Int = 1322
    private val userUpateAddressRequest: Int = 222
    private val registerProductListRq: Int = 2133
    lateinit var context: Context
    var updateUser: Boolean = false

    private fun getUserRegisterProduct() {
        RegisterService(registerProductListRq, this).getRegisterProduct(user.value?.iD)
    }

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
                userAddressList[0].userId = user.value?.iD
                JoyorDb.newInstance(context).addressDao().deleteAddress()
                JoyorDb.newInstance(context).addressDao().addAddress(userAddressList[0])
                getUserRegisterProduct()
            }
            registerProductListRq -> {
                val productRegisterArrayList: ArrayList<RegisterProduct> = Gson().fromJson(data, object : TypeToken<ArrayList<RegisterProduct>>() {}.type)
                if (productRegisterArrayList.size > 0) {
                    JoyorDb.newInstance(context).registerProduct().removeAll()
                    JoyorDb.newInstance(context).registerProduct().addRegisterProduct(productRegisterArrayList)
                    Persister(context).persist(Constants.userProductRegister, true)
                } else
                    Persister(context).persist(Constants.userProductRegister, false)


                getUserUpdateDetail()
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }
}