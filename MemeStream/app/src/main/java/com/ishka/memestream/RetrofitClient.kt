package com.ishka.memestream.api

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private val moshi = Moshi.Builder().build()

    val instance: MemeApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://memestream-api.onrender.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MemeApiService::class.java)
    }
}
