package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.R
import com.joyor.model.CouponDiscount
import com.joyor.service.Results
import com.joyor.service.order.OrderService
import com.joyor.service.setting.SettingService

class CartViewModel : ViewModel(), Results {


    var ischeckOut: MutableLiveData<Boolean> = MutableLiveData()
    var couponCode: MutableLiveData<String> = MutableLiveData()
    var couponApplyRequest: Int = 2211
    var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    var discountGot: MutableLiveData<String> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    lateinit var context: Context


    init {
        couponCode.value = ""
    }

    fun onCheckOut() {
        ischeckOut.value = true
    }

    fun applyCouponCode() {
        if (couponCode.value!!.isNotEmpty()) {
            progressBar.value = true
            OrderService(couponApplyRequest, this).getApplyCoupon(couponCode.value!!)
        } else {
            showToast.value = context.getString(R.string.coupon_required)
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        progressBar.value = false
        val couponDiscount: ArrayList<CouponDiscount> = Gson().fromJson(data, object : TypeToken<ArrayList<CouponDiscount>>() {}.type)
        discountGot.value = couponDiscount[0].couponAmount
    }

    override fun onFailure(requestCode: Int, data: String) {
        progressBar.value = false
        showToast.value = data

    }
}