package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.reflect.TypeToken
import com.joyor.R
import com.joyor.model.Product
import com.joyor.model.Setting
import com.joyor.service.Results
import com.joyor.service.setting.SettingService

class CheckOutViewModel : ViewModel() {


    var checkOut: MutableLiveData<Boolean> = MutableLiveData()
    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    var firstName: MutableLiveData<String> = MutableLiveData()
    var lastName: MutableLiveData<String> = MutableLiveData()
    var companyName: MutableLiveData<String> = MutableLiveData()
    var country: MutableLiveData<String> = MutableLiveData()
    var streetAddress: MutableLiveData<String> = MutableLiveData()
    var townCity: MutableLiveData<String> = MutableLiveData()
    var state: MutableLiveData<String> = MutableLiveData()
    var postCode: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var phone: MutableLiveData<String> = MutableLiveData()
    var additionalInfo: MutableLiveData<String> = MutableLiveData()
    var isManual: MutableLiveData<Boolean> = MutableLiveData()
    var isCountrySelect: MutableLiveData<Boolean> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    var isOrderPlace: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var  context: Context

    init {
        firstName.value = ""
        lastName.value = ""
        companyName.value = ""
        country.value = ""
        streetAddress.value = ""
        townCity.value = ""
        state.value = ""
        postCode.value = ""
        phone.value = ""
        additionalInfo.value = ""
        isManual.value = false
        checkOut.value = false
    }

    fun onPlaceOrder() {

        if (allInputIsOk())
            isOrderPlace.value = true


    }

    private fun allInputIsOk(): Boolean {

        when {
            firstName.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.first_name_required)
                return false

            }
            lastName.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.last_name_required)
                return false
            }
            country.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.country_required)
                return false
            }
            streetAddress.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.street_required)
                return false
            }
            townCity.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.town_required)
                return false
            }
            state.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.state_required)
                return false
            }
            postCode.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.post_required)
                return false
            }
            phone.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.phone_required)
                return false
            }
            email.value!!.isEmpty() -> {
                showToast.value = context.getString(R.string.email_required)
                return false
            }
            else ->
                return true
        }
    }

    fun onCountrySelect() {
        isCountrySelect.value = true
    }

    fun onManual() {
        isManual.value = true
    }

    fun onOnline() {
        isManual.value = false
    }

    fun onBack() {
        isBack.value = true
    }
}