package com.joyor.service.setting

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface SettingClient {

    @GET("support")
    fun getSetting(): Call<ResponseBody>

    @GET("faq")
    fun getFaq(): Call<ResponseBody>
}