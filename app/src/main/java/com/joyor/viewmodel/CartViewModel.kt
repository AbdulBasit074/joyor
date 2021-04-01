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


class CartViewModel : ViewModel(), Results {


    var ischeckOut: MutableLiveData<Boolean> = MutableLiveData()
    var couponCode: MutableLiveData<String> = MutableLiveData()
    var couponApplyRequest: Int = 2211
    var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    var discountGot: MutableLiveData<String> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    lateinit var context: Context

    companion object {
        var isCouponApply: Boolean = false
        var couponCodeApply: String =""
    }

    init {
        couponCode.value = ""
        isCouponApply = false
    }
    fun returnCouponCode(): String {
            return couponCodeApply
    }
    fun onCheckOut() {
        ischeckOut.value = true
    }

    fun applyCouponCode() {
        if (couponCode.value!!.isNotEmpty()) {
            if (!isCouponApply) {
                progressBar.value = true
                couponCodeApply = couponCode.value!!
                OrderService(couponApplyRequest, this).getApplyCoupon(couponCode.value!!)
            }
        } else {
            showToast.value = context.getString(R.string.coupon_required)
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        progressBar.value = false
        val couponDiscount: ArrayList<CouponDiscount> = Gson().fromJson(data, object : TypeToken<ArrayList<CouponDiscount>>() {}.type)
        discountGot.value = couponDiscount[0].couponAmount
        isCouponApply = true
    }
    override fun onFailure(requestCode: Int, data: String) {
        progressBar.value = false
        showToast.value = data

    }
}