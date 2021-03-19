package com.joyor.service.order

import androidx.lifecycle.MutableLiveData
import com.joyor.helper.Constants
import com.joyor.model.PlaceOrder
import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient
import com.joyor.service.setting.SettingClient

class OrderService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack) {

    fun getApplyCoupon(couponCode: String) {
        RetrofitClient.getInstance().create(OrderClient::class.java).getCoupon(couponCode).enqueue(this)
    }

    fun orderPlace(params: PlaceOrder) {
        RetrofitClient.getInstance().create(OrderClient::class.java).placeOrder(Constants.consumerKey, Constants.consumerSecret, params).enqueue(this)
    }


    fun getClientToken() {
        RetrofitClient.getInstance().create(OrderClient::class.java).getClientToken().enqueue(this)
    }

    fun saveNonceKey(nonceKey: String) {
        RetrofitClient.getInstance().create(OrderClient::class.java).saveNonceKey(nonceKey).enqueue(this)
    }


}