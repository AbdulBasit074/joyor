package com.joyor.service.store


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface StoreClient {


    @GET("stores")
    fun getStore(): Call<ResponseBody>

    @GET("products")
    fun getProducts(): Call<ResponseBody>

    @FormUrlEncoded
    @POST("product_detail")
    fun getProductDetails(@Field("product_id") productId: Int?): Call<ResponseBody>

}