package com.github.tehras.data.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutsResponse(
    val limit: Int,
    val page: Int,
    val total: Int,
    val count: Int,
    val page_count: Int,
    val show_previous: Boolean,
    val show_next: Boolean,
    @SerialName("data")
    val workouts: List<Workout>
)

@Serializable
data class Workout(
    val id: String,
    val created_at: Long,
    val end_time: Long,
    val fitness_discipline: String,
    val has_pedaling_metrics: Boolean,
    val has_leaderboard_metrics: Boolean,
    val is_total_work_personal_record: Boolean,
    val name: String,
    val platform: String,
    val start_time: Long,
    val status: String,
    val timezone: String,
    val total_work: Double,
    val user_id: String,
    val created: Long,
    val device_time_created_at: Long,
    @SerialName("peloton")
    val details: Details?
)

@Serializable
data class Details(
    val ride: Ride
)