package com.pdguru.androidparty.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginCredentials(
    @Json(name = "username")
    val username: String,
    @Json(name = "password")
    val password: String
)
