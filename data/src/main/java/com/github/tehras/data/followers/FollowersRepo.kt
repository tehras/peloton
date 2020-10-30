package com.github.tehras.data.followers

import com.github.tehras.data.data.FollowersResponse

interface FollowersRepo {
    suspend fun fetchFollowers(): FollowersResponse
}