package com.github.tehras.data.data

import kotlinx.serialization.Serializable

@Serializable
data class Ride(
    val id: String,
    val title: String,
    val description: String,
    val difficulty_rating_avg: Double,
    val difficulty_rating_count: Long,
    val duration: Int,
    val image_url: String,
    val instructor_id: String?,
    val original_air_time: Long,
    val overall_rating_count: Int,
    val pedaling_duration: Int,
    val scheduled_start_time: Long,
    val fitness_discipline_display_name: String
)