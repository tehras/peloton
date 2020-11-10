package com.github.tehras.data.auth

import com.github.tehras.data.client.ResultWrapper
import com.github.tehras.data.data.AuthResponse

interface AuthRepo {
    fun authData(): AuthResponse?
    fun isLoggedIn(): Boolean
    suspend fun login(username: String, password: String): ResultWrapper<AuthResponse>
}