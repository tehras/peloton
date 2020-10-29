package com.github.tehras.data

import com.github.tehras.data.data.AuthRequest
import com.github.tehras.data.data.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun authenticate(@Body authRequest: AuthRequest): AuthResponse
}