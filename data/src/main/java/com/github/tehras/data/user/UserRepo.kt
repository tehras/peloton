package com.github.tehras.data.user

import com.github.tehras.data.data.User

interface UserRepo {
    suspend fun fetchData(): User
    suspend fun fetchData(userId: String): User
}