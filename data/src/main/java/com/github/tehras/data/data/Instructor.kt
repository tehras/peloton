package com.github.tehras.data.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InstructorResponse(
    val total: Int,
    @SerialName("data")
    val instructors: List<Instructor>
)

@Serializable
data class Instructor(
    val id: String,
    val bio: String,
    val background: String,
    val name: String,
    val image_url: String
)