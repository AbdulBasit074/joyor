package com.joyor.service.setting

import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient

class SettingService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack) {

    fun getSetting() {
        RetrofitClient.getInstance().create(SettingClient::class.java).getSetting().enqueue(this)
    }

    fun getFaq() {
        RetrofitClient.getInstance().create(SettingClient::class.java).getFaq().enqueue(this)
    }

    fun sendContact(
        name: String,
        email: String,
        userId: Int?,
        model: String?,
        serialNumber: String?,
        country: String?,
        datePurchase: String?,
        message: String
    ) {
        RetrofitClient.getInstance().create(SettingClient::class.java).sendContact(name, email,userId,model,serialNumber,country,datePurchase,message).enqueue(this)
    }

}