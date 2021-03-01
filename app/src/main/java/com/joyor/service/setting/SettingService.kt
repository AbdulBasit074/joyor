package com.joyor.service.setting

import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient
import com.joyor.service.auth.StoreClient

class SettingService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack) {

    fun getSetting() {
        RetrofitClient.getInstance().create(SettingClient::class.java).getSetting().enqueue(this)
    }
    fun getFaq() {
        RetrofitClient.getInstance().create(SettingClient::class.java).getFaq().enqueue(this)
    }
}