package com.github.tehras.data.followers

import com.github.tehras.data.PelotonApi
import com.github.tehras.data.data.FollowersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealFollowersRepo(
    private val pelotonApi: PelotonApi
) : FollowersRepo {

    override suspend fun fetchFollowers(
        userId: String
    ): FollowersResponse = withContext(Dispatchers.IO) {
        pelotonApi.followers(userId = userId, page = 0)
    }

    override suspend fun fetchFollowing(
        userId: String
    ): FollowersResponse = withContext(Dispatchers.IO) {
        pelotonApi.following(userId = userId, page = 0)
    }
}