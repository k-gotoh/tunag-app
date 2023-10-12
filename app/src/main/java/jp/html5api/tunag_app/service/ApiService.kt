package jp.html5api.tunag_app.service


import okhttp3.RequestBody

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*;

interface ApiService {
    @POST("login")
    fun login(@Body body: RequestBody): Call<ResponseBody>

    @POST("signUp")
    fun signUp(@Body body: RequestBody): Call<ResponseBody>
//
//    @POST("test")
//    fun test(): Call<ResponseBody>
//    abstract fun login(): Call<ResponseBody>
}