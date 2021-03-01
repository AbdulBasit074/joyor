package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.joyor.helper.Constants
import com.joyor.model.User
import com.joyor.service.Results
import com.joyor.service.auth.AuthService
import org.json.JSONObject

class LoginSignUpViewModel : ViewModel(), Results {

    var isLogin: MutableLiveData<Boolean> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var nameSure: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    var nonceForLoginRequest: Int = 1123
    var nonceForSignUpRequest: Int = 1322
    var loginRequest: Int = 5522
    var signUpRequest: Int = 3322


    init {
        isLogin.value = true
    }

    fun onLoginClick() {
        isLogin.value = true
    }

    fun onSignUpClick() {
        isLogin.value = false
    }

    fun onLoginSignUpClick() {

        if (isLogin.value!!) {
            if (isLoginInputOk()) {
                AuthService(nonceForLoginRequest, this).userNonce(Constants.user, Constants.login)
            }
        } else {
            if (isSignUpOk()) {
                AuthService(nonceForLoginRequest, this).userNonce(
                    Constants.user,
                    Constants.register
                )
            }
        }
    }

    private fun isSignUpOk(): Boolean {
        when {
            !isLoginInputOk() -> {
                return false
            }
            name.value!!.isEmpty() -> {
                showToast.value = "Name is Required"
                return false
            }
            nameSure.value!!.isEmpty() -> {
                showToast.value = "Sure Name is Required"
                return false
            }
            else -> {
                return true
            }
        }

    }

    private fun isLoginInputOk(): Boolean {
        return when {
            email.value!!.isEmpty() -> {
                showToast.value = "Email is required"
                false
            }
            password.value!!.isEmpty() -> {
                showToast.value = "Password is required"
                false
            }
            else -> true
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            nonceForLoginRequest -> {
                val nonce = JSONObject(data).getString("nonce")
                AuthService(loginRequest, this).userLogin(
                    nonce,
                    email.value.toString(),
                    password.value.toString()
                )
            }
            nonceForSignUpRequest -> {
                val nonce = JSONObject(data).getString("nonce")
                AuthService(loginRequest, this).userRegister(
                    name.value!!,
                    nameSure.value!!, password.value!!, email.value!!, nonce
                )
            }
            loginRequest -> {
                user.value = Gson().fromJson(data, User::class.java)
            }
            signUpRequest -> {
                user.value = Gson().fromJson(data, User::class.java)
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }

}