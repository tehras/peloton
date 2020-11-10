package com.github.tehras.data.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutDetailsResponse(
    val id: String,
    val start_time: Long,
    val is_total_work_personal_record: Boolean,
    val status: String,
    val total_work: Double,
    val leaderboard_rank: Int?,
    val total_leaderboard_users: Int,
    @SerialName("achievement_templates")
    val achievements: List<Achievement>,
    val ride: Ride
)

@Serializable
data class Achievement(
    val id: String,
    val name: String,
    val image_url: String,
    val description: String
)