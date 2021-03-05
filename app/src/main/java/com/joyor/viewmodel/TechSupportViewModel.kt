package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joyor.service.Results
import com.joyor.service.setting.SettingService

class TechSupportViewModel : ViewModel(), Results {

    var showToast: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var message: MutableLiveData<String> = MutableLiveData()
    var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    private val sendRequest: Int = 2233
    var back: MutableLiveData<Boolean> = MutableLiveData()


    fun onClickSend() {
        if (isInputOk())
            SettingService(sendRequest, this).sendContact(email.value!!, name.value!!, message.value!!)
    }

    private fun onShowProgress() {
        progressBar.value = true
    }

    private fun onDismissProgress() {
        progressBar.value = false
    }

    private fun isInputOk(): Boolean {

        return when {
            (name.value == null || name.value!!.isEmpty()) -> {
                showToast.value = "Name is Required"
                false
            }
            (email.value == null || name.value!!.isEmpty()) -> {
                showToast.value = "Email is Required"
                false
            }
            (message.value == null || name.value!!.isEmpty()) -> {
                showToast.value = "Password is Required"
                false
            }
            else -> true
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