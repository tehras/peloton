package com.github.tehras.data.overview

import com.github.tehras.data.data.CalendarResponse
import com.github.tehras.data.data.User

interface OverviewRepo {
    suspend fun fetchData(): User
    suspend fun fetchData(userId: String): User
    suspend fun fetchCalendar(): CalendarResponse
    suspend fun fetchCalendar(userId: String): CalendarResponse
}