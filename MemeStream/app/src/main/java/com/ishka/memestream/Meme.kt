
package com.ishka.memestream.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meme(
    val id: String? = null,
    val imageUrl: String? = null,
    val caption: String? = null,
    val userId: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)


@JsonClass(generateAdapter = true)
data class MemePostRequest(
    val imageUrl: String,
    val caption: String,
    val userId: String,
    val latitude: Double?,
    val longitude: Double?
)
