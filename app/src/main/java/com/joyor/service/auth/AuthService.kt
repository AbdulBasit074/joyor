package com.joyor.service.auth

import android.provider.ContactsContract
import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient


class AuthService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack) {


    fun userNonce(controller: String, method: String) {
        RetrofitClient.getInstance().create(AuthClient::class.java)
            .userNonce(controller, method)
            .enqueue(this)
    }
    fun userRegister(first_name:String,last_name:String,user_pass:String,email: String, nonce: String) {
        RetrofitClient.getInstance().create(AuthClient::class.java)
            .userRegister(first_name,last_name,user_pass,email,nonce)
            .enqueue(this)
    }



}