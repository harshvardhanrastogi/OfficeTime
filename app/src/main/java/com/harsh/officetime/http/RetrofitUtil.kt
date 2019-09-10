package com.harsh.officetime.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {

    @JvmStatic
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://us-central1-officetime-a1dd2.cloudfunctions.net/officetime/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @JvmStatic
    val webApiService: WebApiService = retrofit.create(WebApiService::class.java)
}