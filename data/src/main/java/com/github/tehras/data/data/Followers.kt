package com.github.tehras.data.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowersResponse(
    val limit: Int,
    val page: Int,
    val page_count: Int,
    val total: Int,
    @SerialName("data")
    val followers: List<Follower>
)

@Serializable
data class Follower(
    val id: String,
    val image_url: String,
    val is_profile_private: Boolean,
    val location: String,
    val total_followers: Int,
    val total_following: Int,
    val total_workouts: Int,
    val username: String,
    val authed_user_follows: Boolean,
    val relationship: Relationship
)

@Serializable
data class Relationship(
    val me_to_user: String,
    val user_to_me: String
)