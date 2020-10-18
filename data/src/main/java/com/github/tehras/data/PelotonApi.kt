package com.github.tehras.data

import retrofit2.http.GET
import retrofit2.http.Path

interface PelotonApi {
    @GET("https://api.onepeloton.com/api/user/{user}/overview")
    suspend fun overview(@Path("user") user: String) :
}