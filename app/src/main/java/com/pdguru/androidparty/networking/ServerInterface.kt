package com.pdguru.androidparty.networking

import com.pdguru.androidparty.model.LoginCredentials
import com.pdguru.androidparty.model.LoginToken
import com.pdguru.androidparty.model.ServerLocations
import retrofit2.Response
import retrofit2.http.*

interface ServerInterface {

    @GET("/servers")
    suspend fun getServers(): Response<List<ServerLocations>>

    @Headers("Content-Type: application/json")
    @POST("/tokens")
    suspend fun login(@Body body: LoginCredentials): Response<LoginToken>
}
