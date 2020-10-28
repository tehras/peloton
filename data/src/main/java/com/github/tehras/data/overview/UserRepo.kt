package com.github.tehras.data.overview

import com.github.tehras.data.data.User

interface UserRepo {
    suspend fun fetchData(): User
}