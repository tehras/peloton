package com.github.tehras.data.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("username_or_email")
    val username: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val session_id: String,
    val user_id: String
)