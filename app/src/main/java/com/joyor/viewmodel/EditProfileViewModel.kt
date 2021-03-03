package com.joyor.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
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

    fun onLogout() {
        isLogout.value = true
    }

    fun onBackClick() {
        onBack.value = true
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
        if (user.value?.firstName == null || user.value?.firstName!!.isEmpty()) {
            showToast.value = "Name is empty"
            return false
        } else if (user.value?.lastName == null || user.value?.lastName!!.isEmpty()) {
            showToast.value = "Sure Namer is empty"
            return false
        } else if (user.value?.email == null || user.value?.email!!.isEmpty()) {
            showToast.value = "Email is empty"
            return false
        } else {
            return true
        }

    }
}