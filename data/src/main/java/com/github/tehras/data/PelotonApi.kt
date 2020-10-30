package com.github.tehras.data

import com.github.tehras.data.data.FollowersResponse
import com.github.tehras.data.data.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PelotonApi {
    @GET("/api/user/{userName}")
    suspend fun user(@Path("userName") userName: String): User

    @GET("/api/user/{userId}/followers")
    suspend fun followers(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 50
    ): FollowersResponse
}