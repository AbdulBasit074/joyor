package com.joyor.service.auth


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AuthClient {


    @GET("get_nonce")
    fun userNonce(
        @Query("controller") user: String,
        @Query("method") login: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/register")
    fun userRegister(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("user_pass") user_pass: String,
        @Field("email") email: String,
        @Field("nonce") nonce: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/login")
    fun userLogin(
        @Field("nonce") first_name: String,
        @Field("email") last_name: String,
        @Field("password") user_pass: String
    ): Call<ResponseBody>




}