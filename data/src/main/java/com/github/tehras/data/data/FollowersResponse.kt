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