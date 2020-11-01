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
    val peloton_id: String,
    val platform: String,
    val start_time: Long,
    val status: String,
    val timezone: String,
    val total_work: Double,
    val user_id: String,
    val workout_type: String,
    val created: Long,
    val device_time_created_at: Long,
    @SerialName("peloton")
    val details: Details
)

@Serializable
data class Details(
    val ride: Ride
)

@Serializable
data class Ride(
    val title: String,
    val description: String,
    val difficulty_rating_avg: Double,
    val difficulty_rating_count: Long,
    val duration: Int,
    val has_pedaling_metrics: Boolean,
    val home_peloton_id: String?,
    val id: String,
    val image_url: String,
    val instructor_id: String?,
    val original_air_time: Long,
    val overall_rating_avg: Double,
    val overall_rating_count: Int,
    val pedaling_duration: Int,
    val scheduled_start_time: Long,
    val total_workouts: Long,
    val total_in_progress_workouts: Long,
    val fitness_discipline_display_name: String
)