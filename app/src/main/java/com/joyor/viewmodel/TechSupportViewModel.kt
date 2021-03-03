package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Faq
import com.joyor.service.Results
import com.joyor.service.setting.SettingService

class TechSupportViewModel : ViewModel(), Results {

    var showToast: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var message: MutableLiveData<String> = MutableLiveData()
    private val sendRequest: Int = 2233
    var back: MutableLiveData<Boolean> = MutableLiveData()

    fun onClickSend() {
        if (isInputOk())
            SettingService(sendRequest, this).sendContact(email.value!!, name.value!!, message.value!!)
    }

    private fun isInputOk(): Boolean {

        when {
            (name.value == null || name.value!!.isEmpty()) -> {
                showToast.value = "Name is Required"
                return false
            }
            (email.value == null || name.value!!.isEmpty()) -> {
                showToast.value = "Email is Required"
                return false
            }
            (message.value == null || name.value!!.isEmpty()) -> {
                showToast.value = "Password is Required"
                return false
            }
            else -> return true
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            sendRequest -> {
                showToast.value = "Message Sent"
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }


}