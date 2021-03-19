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

class SignUpViewModel : ViewModel(), Results {

    var userRegisterNonceRequest: Int = 2211
    var userRegisterRequest: Int = 1211
    var user: MutableLiveData<User> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var nameSure: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var isSkip: Boolean = true
    var showToast: MutableLiveData<String> = MutableLiveData()
    var saveUserInDb: Boolean = false


    fun onClick(view: View) {
        if (isSkip)
            view.context.moveTo(HomeActivity::class.java)
        else {
            if (isInputOk())
                AuthService(userRegisterNonceRequest, this).userNonce(
                    Constants.user,
                    Constants.register
                )
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            userRegisterNonceRequest -> {
                val nonce = JSONObject(data).getString("nonce")
                AuthService(userRegisterRequest, this).userRegister(
                    name.value!!,
                    nameSure.value!!, password.value!!, email.value!!, nonce
                )
            }
            userRegisterRequest -> {
                saveUserInDb = true
                user.value = Gson().fromJson(data, User::class.java)
                showToast.value = "User Register"
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }

    private fun isInputOk(): Boolean {
        return if (name.value == null || name.value!!.isEmpty()) {
            showToast.value = "Name is empty"
            false
        } else if (nameSure.value == null || nameSure.value!!.isEmpty()) {
            showToast.value = "Sure Namer is empty"
            false
        } else if (email.value == null || email.value!!.isEmpty()) {
            showToast.value = "Email is empty"
            false
        } else if (password.value == null || password.value!!.isEmpty()) {
            showToast.value = "Password is empty"
            false
        } else {
            true
        }

    }
}