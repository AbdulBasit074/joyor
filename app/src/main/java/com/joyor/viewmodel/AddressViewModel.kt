package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.reflect.TypeToken
import com.joyor.R
import com.joyor.model.Address
import com.joyor.model.Product
import com.joyor.model.Setting
import com.joyor.model.User
import com.joyor.model.room.JoyorDb
import com.joyor.service.Results
import com.joyor.service.auth.AuthService
import com.joyor.service.setting.SettingService

class AddressViewModel : ViewModel(), Results {

    var userAddress: MutableLiveData<Address> = MutableLiveData()
    var userLogged: MutableLiveData<User> = MutableLiveData()
    var pressAddress: MutableLiveData<Boolean> = MutableLiveData()
    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    var isCountrySelect: MutableLiveData<Boolean> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var isOrderPlace: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var context: Context
    private var addAddressRequest: Int = 2811

    private fun onShowProgress() {
        showProgress.value = true
    }

    private fun onDismissProgress() {
        showProgress.value = false
    }

    fun onPressAddress() {
        if (allInputIsOk()) {
            onShowProgress()
            userAddress.value!!.userId = userLogged.value!!.iD
            AuthService(addAddressRequest, this).addUserAddress(userAddress.value!!)
        }
    }

    private fun allInputIsOk(): Boolean {

        when {
            userAddress.value == null || userAddress.value!!.billing!!.firstName!!.isEmpty() -> {
                showToast.value = context.getString(R.string.first_name_required)
                return false
            }
            userAddress.value!!.billing!!.lastName!!.isEmpty() -> {
                showToast.value = context.getString(R.string.last_name_required)
                return false
            }
            userAddress.value!!.billing!!.country!!.isEmpty() -> {
                showToast.value = context.getString(R.string.country_required)
                return false
            }
            userAddress.value!!.billing!!.address1!!.isEmpty() -> {
                showToast.value = context.getString(R.string.street_required)
                return false
            }
            userAddress.value!!.billing!!.city!!.isEmpty() -> {
                showToast.value = context.getString(R.string.town_required)
                return false
            }
            userAddress.value!!.billing!!.postcode!!.isEmpty() -> {
                showToast.value = context.getString(R.string.post_required)
                return false
            }
            userAddress.value!!.billing!!.phone!!.isEmpty() -> {
                showToast.value = context.getString(R.string.phone_required)
                return false
            }
            userAddress.value!!.billing?.email!!.isEmpty() -> {
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

    fun onBack() {
        isBack.value = true
    }

    override fun onSuccess(requestCode: Int, data: String) {
        onDismissProgress()
        when (requestCode) {
            addAddressRequest -> {
                showToast.value = context.getString(R.string.add_address)
                JoyorDb.newInstance(context).addressDao().deleteAddress()
                JoyorDb.newInstance(context).addressDao().addAddress(userAddress.value!!)
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}