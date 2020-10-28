package com.github.tehras.data

import com.github.tehras.data.data.User
import retrofit2.http.GET
import retrofit2.http.Path

interface PelotonApi {
    @GET("/api/user/{userName}")
    suspend fun user(@Path("userName") userName: String): User

    @GET("https://api.onepeloton.com/api/user/{user}/overview")
    suspend fun overview(@Path("user") user: String): String
}