package com.joyor.service.auth

import com.joyor.model.Address
import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient


class AuthService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack) {


    fun userNonce(controller: String, method: String) {
        RetrofitClient.getInstance().create(AuthClient::class.java)
            .userNonce(controller, method)
            .enqueue(this)
    }

    fun userRegister(
        first_name: String,
        last_name: String,
        user_pass: String,
        email: String,
        nonce: String
    ) {
        RetrofitClient.getInstance().create(AuthClient::class.java)
            .userRegister(first_name, last_name, user_pass, email, nonce)
            .enqueue(this)
    }

    fun userLogin(nonce: String, email: String, user_pass: String) {
        RetrofitClient.getInstance().create(AuthClient::class.java).userLogin(nonce, email, user_pass).enqueue(this)
    }

    fun userUpdate(userId: Int, user_pass: String, email: String, nonce: String, first_name: String, last_name: String) {
        RetrofitClient.getInstance().create(AuthClient::class.java).userUpdate(userId, user_pass, email, nonce, first_name, last_name).enqueue(this)
    }

    fun userDetail(userId: Int) {
        RetrofitClient.getInstance().create(AuthClient::class.java).userDetail(userId).enqueue(this)
    }

    fun getUserAddress(userId: Int) {
        RetrofitClient.getInstance().create(AuthClient::class.java).getAddress(userId).enqueue(this)
    }

    fun addUserAddress(params: Address) {
        RetrofitClient.getInstance().create(AuthClient::class.java).addAddress(params).enqueue(this)
    }

    fun changePassword(
        userId: Int,
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        RetrofitClient.getInstance().create(AuthClient::class.java).changePassword(userId,oldPassword, newPassword,confirmPassword).enqueue(this)
    }

    fun forgotPassword(email: String) {
        RetrofitClient.getInstance().create(AuthClient::class.java).forgotPassword(email).enqueue(this)
    }


}