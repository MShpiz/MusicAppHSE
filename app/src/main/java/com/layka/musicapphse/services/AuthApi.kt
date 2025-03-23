package com.layka.musicapphse.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body data: LoginData): Response<LoginResult>

    @POST("/auth/register")
    suspend fun register(@Body data: RegisterData): Response<RegisterResult>
}