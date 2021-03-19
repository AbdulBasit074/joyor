package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joyor.R
import com.joyor.model.RegisterProduct
import com.joyor.model.User
import com.joyor.service.Results
import com.joyor.service.setting.SettingService

class TechSupportViewModel : ViewModel(), Results {

    var showToast: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User?> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var message: MutableLiveData<String> = MutableLiveData()
    var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    private val sendRequest: Int = 2233
    var back: MutableLiveData<Boolean> = MutableLiveData()
    var moveForLogin: MutableLiveData<Boolean> = MutableLiveData()
    var moveForRegister: MutableLiveData<Boolean> = MutableLiveData()
    var selectProduct: MutableLiveData<Boolean> = MutableLiveData()
    var registerSelectedProduct: MutableLiveData<RegisterProduct?> = MutableLiveData()
    var sendOk: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var context: Context

    init {
        registerSelectedProduct.value = null
    }

    fun onClickSend() {
        if (isInputOk()) {
            onShowProgress()
            SettingService(sendRequest, this).sendContact(
                name.value!!,
                email.value!!,
                user.value?.iD,
                registerSelectedProduct.value?.model,
                registerSelectedProduct.value?.serialNumber,
                registerSelectedProduct.value?.country,
                registerSelectedProduct.value?.datePurchase,
                message.value!!
            )
        }
    }

    fun onMoveForLogin() {
        moveForLogin.value = true
    }

    fun onMoveForRegisterProducts() {
        moveForRegister.value = true
    }

    fun onSelectProduct() {
        selectProduct.value = true
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
                showToast.value = context.getString(R.string.name_required)
                false
            }
            (email.value == null || email.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.email_required)
                false
            }
            (message.value == null || message.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.message_required)
                false
            }
            registerSelectedProduct.value == null -> {
                showToast.value = context.getString(R.string.register_product_required)
                false
            }
            else -> true
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        onDismissProgress()
        when (requestCode) {
            sendRequest -> {
                name.value = ""
                email.value = ""
                message.value = ""
                showToast.value = data
                sendOk.value = true
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}