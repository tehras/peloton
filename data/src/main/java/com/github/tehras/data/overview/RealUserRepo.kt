package com.github.tehras.data.overview

import com.github.tehras.data.PelotonApi
import com.github.tehras.data.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealUserRepo(
    private val pelotonApi: PelotonApi
) : UserRepo {
    override suspend fun fetchData(): User = withContext(Dispatchers.IO) {
        @Suppress("UNUSED_VARIABLE") val user = pelotonApi.user("7ehras")

        user
    }
}