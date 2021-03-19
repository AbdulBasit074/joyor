package com.joyor.service.order

import com.joyor.model.PlaceOrder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface OrderClient {


    @FormUrlEncoded
    @POST("get_coupon_data")
    fun getCoupon(
        @Field("coupon_code") couponCode: String
    ): Call<ResponseBody>


    @POST("create_order")
    fun placeOrder(
        @Query("consumer_key") consumerKey: String,
        @Query("consumer_secret") consumerSecret: String,
        @Body body: PlaceOrder
    ): Call<ResponseBody>


    @GET("get_client_token")
    fun getClientToken(): Call<ResponseBody>

    @FormUrlEncoded
    @POST("paymentMethodNonce")
    fun saveNonceKey(@Field("paymentmethodToken") nonceKey: String): Call<ResponseBody>


}