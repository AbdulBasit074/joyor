package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.R
import com.joyor.helper.Constants
import com.joyor.helper.Persister
import com.joyor.model.Address
import com.joyor.model.RegisterProduct
import com.joyor.model.User
import com.joyor.model.room.JoyorDb
import com.joyor.service.Results
import com.joyor.service.auth.AuthService
import com.joyor.service.register.RegisterService
import org.json.JSONObject

class LoginSignUpViewModel : ViewModel(), Results {

    var isLogin: MutableLiveData<Boolean> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var forgotPasswordClicked: MutableLiveData<Boolean> = MutableLiveData()
    var userAddress: MutableLiveData<Address> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var nameSure: MutableLiveData<String> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    var saveRegister: MutableLiveData<ArrayList<RegisterProduct>> = MutableLiveData()
    var isSignUp: MutableLiveData<Boolean> = MutableLiveData()

    var nonceForLoginRequest: Int = 1123
    var nonceForSignUpRequest: Int = 1322
    var userAddressRequest: Int = 3772
    var userRegisterProduct: Int = 3472
    var loginRequest: Int = 5522
    var signUpRequest: Int = 3322

    lateinit var context: Context

    init {
        isLogin.value = true
    }

    fun onShowProgress() {
        showProgress.value = true
    }

    fun onDismissProgress() {
        showProgress.value = false
    }

    fun onBack() {
        isBack.value = true
    }

    fun onLoginClick() {
        isLogin.value = true
    }

    fun onSignUpClick() {
        isLogin.value = false
    }

    fun onForgotPasswordClicked() {
        forgotPasswordClicked.value = true
    }

    fun onLoginSignUpClick() {

        if (isLogin.value!!) {
            if (isLoginInputOk()) {
                onShowProgress()
                AuthService(nonceForLoginRequest, this).userNonce(Constants.user, Constants.login)
            }
        } else {
            if (isSignUpOk()) {
                onShowProgress()
                AuthService(nonceForSignUpRequest, this).userNonce(
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
            (name.value == null || name.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.name_required)
                return false
            }
            (nameSure.value == null || nameSure.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.last_name)
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun isLoginInputOk(): Boolean {
        return when {
            (email.value == null || email.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.email_required)
                false
            }
            (password.value == null || password.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.password_required)
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
                AuthService(signUpRequest, this).userRegister(
                    name.value!!,
                    nameSure.value!!, password.value!!, email.value!!, nonce
                )
            }
            loginRequest -> {
                user.value = Gson().fromJson(data, User::class.java)
                RegisterService(userRegisterProduct, this).getRegisterProduct(user.value?.iD)
            }
            userRegisterProduct -> {
                val productRegisterArrayList: ArrayList<RegisterProduct> = Gson().fromJson(data, object : TypeToken<ArrayList<RegisterProduct>>() {}.type)
                if (productRegisterArrayList.size > 0) {
                    Persister(context).persist(Constants.userProductRegister, true)
                    saveRegister.value = productRegisterArrayList
                } else
                    Persister(context).persist(Constants.userProductRegister, false)

                AuthService(userAddressRequest, this).getUserAddress(user.value?.iD!!)
            }
            userAddressRequest -> {
                onDismissProgress()
                val userAddressList: ArrayList<Address> = Gson().fromJson(data, object : TypeToken<ArrayList<Address>>() {}.type)
                userAddress.value = userAddressList[0]
            }
            signUpRequest -> {
                onDismissProgress()
                user.value = Gson().fromJson(data, User::class.java)
                isSignUp.value = true
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }

}