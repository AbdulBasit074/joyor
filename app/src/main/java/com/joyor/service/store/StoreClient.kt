package com.joyor.service.auth


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface StoreClient {


    @GET("stores")
    fun getStore(): Call<ResponseBody>

    @GET("products")
    fun getProduct(): Call<ResponseBody>

}