package com.github.tehras.data.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val location: String,
    val image_url: String,
    val total_following: Int,
    val total_followers: Int,
    val tags_info: TagInfo
) {
    @Serializable
    data class TagInfo(
        val primary_name: String,
        val total_joined: Int
    )
}