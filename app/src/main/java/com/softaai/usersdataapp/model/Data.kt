package com.softaai.usersdataapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Data(
    @Json(name = "avatar")
    var avatar: String,
    @Json(name = "email")
    var email: String,
    @Json(name = "first_name")
    var firstName: String,
    @field:PrimaryKey
    @Json(name = "id")
    val id: Int,
    @Json(name = "last_name")
    var lastName: String
)