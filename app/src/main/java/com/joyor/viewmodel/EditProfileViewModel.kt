package com.joyor.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.joyor.R
import com.joyor.helper.Constants
import com.joyor.helper.moveTo
import com.joyor.model.User
import com.joyor.service.Results
import com.joyor.service.auth.AuthService
import com.joyor.service.auth.StoreService
import com.joyor.ui.HomeActivity
import org.json.JSONObject

class EditProfileViewModel : ViewModel(), Results {

    var userUpdateNonceRequest: Int = 2211
    var userUpdateRequest: Int = 1211
    var user: MutableLiveData<User> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var isUpdate: MutableLiveData<Boolean> = MutableLiveData()
    var nameSure: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var isLogout: MutableLiveData<Boolean> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    var onBack: MutableLiveData<Boolean> = MutableLiveData()
    var isProgress: MutableLiveData<Boolean> = MutableLiveData()
    var addressOpen: MutableLiveData<Boolean> = MutableLiveData()
    var languageSelect: MutableLiveData<Boolean> = MutableLiveData()
    var changePassword: MutableLiveData<Boolean> = MutableLiveData()
    var registerProduct: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var context: Context

    fun onShowProgress() {
        isProgress.value = true
    }

    fun onDismissProgress() {
        isProgress.value = false
    }

    fun onLanguageSelect() {
        languageSelect.value = true
    }

    fun onChangePassword() {
        changePassword.value = true
    }

    fun onRegisterProduct() {
        registerProduct.value = true
    }

    fun onLogout() {
        isLogout.value = true
    }

    fun onBackClick() {
        onBack.value = true
    }

    fun onAddressOpen() {
        addressOpen.value = true
    }

    fun onUpdate() {
        if (isInputOk())
            if (isInputOk())
                AuthService(userUpdateNonceRequest, this).userNonce(Constants.user, Constants.update)
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            userUpdateNonceRequest -> {
                val nonce = JSONObject(data).getString("nonce")
                if (password.value == null)
                    password.value = ""
                AuthService(userUpdateRequest, this).userUpdate(
                    user.value!!.iD!!, user_pass = password.value!!, email = user.value?.email!!, nonce = nonce, first_name = user.value?.firstName!!,
                    last_name = user.value?.lastName!!
                )
            }
            userUpdateRequest -> {
                isUpdate.value = true
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }

    private fun isInputOk(): Boolean {
        return if (user.value?.firstName == null || user.value?.firstName!!.isEmpty()) {
            showToast.value = context.getString(R.string.first_name_required)
            false
        } else if (user.value?.lastName == null || user.value?.lastName!!.isEmpty()) {
            showToast.value = context.getString(R.string.last_name_required)
            false
        } else if (user.value?.email == null || user.value?.email!!.isEmpty()) {
            showToast.value = context.getString(R.string.email_required)
            false
        } else {
            true
        }

    }
}