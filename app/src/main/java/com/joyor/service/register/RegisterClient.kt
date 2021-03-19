package com.joyor.service.register

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterClient {
    @FormUrlEncoded
    @POST("add_user_registered_products")
    fun registerProduct(
        @Field("user_id") userID: Int?,
        @Field("model") model: String,
        @Field("serial_number") serialNumber: String,
        @Field("country") country: String,
        @Field("date_purchase") datePurchase: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("get_user_registered_products")
    fun getRegisterProduct(
        @Field("user_id") userID: Int?
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("delete_user_registered_products")
    fun deleteRegisterProduct(
        @Field("id") id: String,
        @Field("user_id") userID: Int?
    ): Call<ResponseBody>
}