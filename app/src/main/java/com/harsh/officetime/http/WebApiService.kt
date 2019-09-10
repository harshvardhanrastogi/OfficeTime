package com.harsh.officetime.http

import com.harsh.officetime.http.models.RequestBody
import com.harsh.officetime.http.models.ResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WebApiService {

    @POST("users")
    fun signup(@Body body: RequestBody.SignUpBody): Call<ResponseModel.SimpleResponse>
}