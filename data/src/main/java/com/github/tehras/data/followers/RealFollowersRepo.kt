package com.github.tehras.data.followers

import com.github.tehras.data.PelotonApi
import com.github.tehras.data.auth.AuthRepo
import com.github.tehras.data.data.FollowersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealFollowersRepo(
    private val pelotonApi: PelotonApi,
    private val authRepo: AuthRepo
) : FollowersRepo {
    private val userId
        get() = authRepo.authData()!!.user_id

    override suspend fun fetchFollowers(): FollowersResponse = withContext(Dispatchers.IO) {
        pelotonApi.followers(userId = userId, page = 0)
    }
}