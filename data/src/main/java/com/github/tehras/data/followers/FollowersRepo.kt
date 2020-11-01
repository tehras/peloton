package com.github.tehras.data.followers

import com.github.tehras.data.data.FollowersResponse

interface FollowersRepo {
    suspend fun fetchFollowers(userId: String): FollowersResponse
    suspend fun fetchFollowing(userId: String): FollowersResponse
}