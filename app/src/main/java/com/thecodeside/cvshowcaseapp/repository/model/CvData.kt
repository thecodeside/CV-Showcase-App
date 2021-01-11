package com.thecodeside.cvshowcaseapp.repository.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// TODO: 1/10/21 Design proper API 
@JsonClass(generateAdapter = true)
data class CvData(
    @Json(name = "email")
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "summary")
    val summary: String,
)
