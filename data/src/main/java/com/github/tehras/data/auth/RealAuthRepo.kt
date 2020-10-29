package com.github.tehras.data.auth

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.github.tehras.data.AuthApi
import com.github.tehras.data.data.AuthRequest
import com.github.tehras.data.data.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealAuthRepo(
    private val authApi: AuthApi,
    context: Context
) : AuthRepo {
    private var authDataCache: AuthResponse? = null
    private val sharedPrefs = context.getSharedPreferences("auth", MODE_PRIVATE)

    override fun authData(): AuthResponse? {
        if (authDataCache != null) {
            return authDataCache
        }

        val sessionId = sharedPrefs.getString("session_id", null)
        val userID = sharedPrefs.getString("user_id", null)

        if (sessionId == null || userID == null) {
            return null
        }

        return AuthResponse(session_id = sessionId, user_id = userID).also {
            authDataCache = it
        }
    }

    override fun isLoggedIn(): Boolean = authData() != null
    override suspend fun login(
        username: String,
        password: String
    ): AuthResponse = withContext(Dispatchers.IO) {
        authApi.authenticate(
            authRequest = AuthRequest(
                username = username,
                password = password
            )
        ).also {
            // save result to cache.
            saveResult(it)
        }
    }

    private fun saveResult(result: AuthResponse) {
        sharedPrefs.edit()
            .putString(SESSION_ID, result.session_id)
            .putString(USER_ID, result.user_id)
            .apply()

        authDataCache = result
    }

    companion object {
        private const val SESSION_ID = "session_id"
        private const val USER_ID = "user_id"
    }
}