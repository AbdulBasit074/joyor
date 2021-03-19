package com.joyor.service.register

import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient

class RegisterService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack) {


    fun registerProduct(userID: Int?, model: String, serialNumber: String, country: String, date: String) {
        RetrofitClient.getInstance().create(RegisterClient::class.java).registerProduct(userID, model, serialNumber, country, date).enqueue(this)
    }

    fun getRegisterProduct(userID: Int?) {
        RetrofitClient.getInstance().create(RegisterClient::class.java).getRegisterProduct(userID).enqueue(this)
    }
    fun deleteRegisterProduct(productId: String, userID: Int?) {
        RetrofitClient.getInstance().create(RegisterClient::class.java).deleteRegisterProduct(productId,userID).enqueue(this)
    }


}