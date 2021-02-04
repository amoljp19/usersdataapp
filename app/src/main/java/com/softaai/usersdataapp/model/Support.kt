package com.softaai.usersdataapp.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Support(
    @Json(name = "text")
    val text: String,
    @Json(name = "url")
    val url: String
)