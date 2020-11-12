package com.github.tehras.data.data

import kotlinx.serialization.Serializable

@Serializable
data class CalendarResponse(
    val months: List<Month>
)

@Serializable
data class Month(
    val year: Int,
    val month: Int,
    val active_days: List<Int>
)