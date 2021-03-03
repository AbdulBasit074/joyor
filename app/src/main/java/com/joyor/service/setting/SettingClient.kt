package com.joyor.service.setting

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SettingClient {

    @GET("support")
    fun getSetting(): Call<ResponseBody>

    @GET("faq")
    fun getFaq(): Call<ResponseBody>

    @FormUrlEncoded
    @POST("submit_data")
    fun sendContact(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("message") message: String
    ): Call<ResponseBody>

}