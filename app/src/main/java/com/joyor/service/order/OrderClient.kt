package com.joyor.service.order

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderClient {


    @FormUrlEncoded
    @POST("get_coupon_data")
    fun getCoupon(
        @Field("coupon_code") couponCode: String
    ): Call<ResponseBody>
}