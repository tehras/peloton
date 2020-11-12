package com.github.tehras.data.overview

import com.github.tehras.data.PelotonApi
import com.github.tehras.data.auth.AuthRepo
import com.github.tehras.data.data.CalendarResponse
import com.github.tehras.data.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealOverviewRepo(
    private val pelotonApi: PelotonApi,
    private val authRepo: AuthRepo
) : OverviewRepo {
    override suspend fun fetchData(): User = withContext(Dispatchers.IO) {
        fetchData(authRepo.authData()!!.user_id)
    }

    override suspend fun fetchData(userId: String): User = withContext(Dispatchers.IO) {
        pelotonApi.user(userId)
    }

    override suspend fun fetchCalendar(): CalendarResponse {
        return fetchCalendar(authRepo.authData()!!.user_id)
    }

    override suspend fun fetchCalendar(userId: String): CalendarResponse {
        return pelotonApi.calendar(userId)
    }
}