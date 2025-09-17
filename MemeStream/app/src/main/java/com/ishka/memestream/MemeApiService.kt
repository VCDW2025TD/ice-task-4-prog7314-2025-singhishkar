package com.ishka.memestream.api

import com.ishka.memestream.model.Meme
import com.ishka.memestream.model.MemePostRequest
import retrofit2.Response
import retrofit2.http.*

interface MemeApiService {

//    @GET("memes")
//    suspend fun getMemes(): List<Meme>

    @POST("memes")
    suspend fun postMeme(@Body meme: MemePostRequest): Response<Unit>

    @GET("memes")
    suspend fun getMemes(@Query("userId") userId: String? = null): List<Meme>

}
