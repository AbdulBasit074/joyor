package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joyor.R
import com.joyor.model.User
import com.joyor.service.Results
import com.joyor.service.register.RegisterService

class RegisterNewProductViewModel : ViewModel(), Results {

    var showToast: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var back: MutableLiveData<Boolean> = MutableLiveData()
    var registerProduct: MutableLiveData<Boolean> = MutableLiveData()
    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var model: MutableLiveData<String> = MutableLiveData()
    var serialNumber: MutableLiveData<String> = MutableLiveData()
    var country: MutableLiveData<String> = MutableLiveData()
    var purchaseDate: MutableLiveData<String> = MutableLiveData()
    var datePurchase: MutableLiveData<Boolean> = MutableLiveData()
    var isModelSelect: MutableLiveData<Boolean> = MutableLiveData()
    var isCountrySelect: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var context: Context
    private val registerProducts: Int = 2221


    private fun onShowProgress() {
        showProgress.value = true
    }

    fun onDateSelect() {
        datePurchase.value = true
    }

    private fun onDismissProgress() {
        showProgress.value = false
    }

    fun onBack() {
        back.value = true
    }

    fun onProductRegister() {
        if (isInputOk()) {
            onShowProgress()
            RegisterService(registerProducts, this).registerProduct(user.value!!.iD, model.value!!, serialNumber.value!!, country.value!!, purchaseDate.value!!)
        }
    }

    fun onModelSelect() {
        isModelSelect.value = true
    }

    fun onCountrySelect() {
        isCountrySelect.value = true
    }

    private fun isInputOk(): Boolean {

        when {
            (model.value == null || model.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.model_required)
                return false
            }
            (serialNumber.value == null || serialNumber.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.serial_required)
                return false
            }
            (country.value == null || country.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.country_required)
                return false
            }
            (purchaseDate.value == null || purchaseDate.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.date_purchase)
                return false
            }
            else -> return true
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        onDismissProgress()
        when (requestCode) {
            registerProducts -> {
                registerProduct.value = true
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}

