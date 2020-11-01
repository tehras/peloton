package com.github.tehras.data.user

import com.github.tehras.data.PelotonApi
import com.github.tehras.data.auth.AuthRepo
import com.github.tehras.data.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealUserRepo(
    private val pelotonApi: PelotonApi,
    private val authRepo: AuthRepo
) : UserRepo {
    override suspend fun fetchData(): User = withContext(Dispatchers.IO) {
        fetchData(authRepo.authData()!!.user_id)
    }

    override suspend fun fetchData(userId: String): User = withContext(Dispatchers.IO) {
        pelotonApi.user(userId)
    }
}