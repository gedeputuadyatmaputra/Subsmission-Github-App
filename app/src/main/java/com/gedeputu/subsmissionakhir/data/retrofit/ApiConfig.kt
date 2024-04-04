package com.gedeputu.subsmissionakhir.data.retrofit

import com.gedeputu.subsmissionakhir.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL = BuildConfig.BASE_URL

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)
}