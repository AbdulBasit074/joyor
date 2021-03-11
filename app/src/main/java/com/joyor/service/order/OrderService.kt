package com.joyor.service.order

import androidx.lifecycle.MutableLiveData
import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient
import com.joyor.service.setting.SettingClient

class OrderService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack) {

    fun getApplyCoupon(couponCode: String) {
        RetrofitClient.getInstance().create(OrderClient::class.java).getCoupon(couponCode).enqueue(this)
    }
}