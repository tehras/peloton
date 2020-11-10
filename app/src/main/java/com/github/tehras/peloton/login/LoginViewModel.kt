package com.github.tehras.peloton.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.auth.AuthRepo
import com.github.tehras.data.client.ResultWrapper.*
import com.github.tehras.peloton.login.LoginState.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class LoginViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {
    val loginState = MutableStateFlow<LoginState>(EnteringInfo)

    fun authenticate(username: String, password: String) {
        loginState.value = SubmittingInfo

        viewModelScope.launch {
            when (val response = authRepo.login(username = username, password = password)) {
                is Success -> loginState.value = FinishedSuccessfully
                is GenericError ->
                    LoginError(response.message ?: "Could not authenticate at this time.")
                NetworkError -> LoginError()
            }
        }
    }
}

sealed class LoginState {
    object EnteringInfo : LoginState()
    object SubmittingInfo : LoginState()
    object FinishedSuccessfully : LoginState()
    data class LoginError(val message: String = "Could not authenticate at this time.") :
        LoginState()
}