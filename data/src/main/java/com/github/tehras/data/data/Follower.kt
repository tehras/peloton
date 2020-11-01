package com.github.tehras.data.data

import kotlinx.serialization.Serializable

@Serializable
data class Follower(
    val id: String,
    val image_url: String,
    val is_profile_private: Boolean,
    val location: String,
    val username: String,
    val authed_user_follows: Boolean,
    val relationship: Relationship
)

@Serializable
data class Relationship(
    val me_to_user: String?,
    val user_to_me: String?
)