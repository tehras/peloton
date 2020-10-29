package com.github.tehras.peloton.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.auth.AuthRepo
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
            authRepo.login(username = username, password = password)

            loginState.value = FinishedSuccessfully
        }
    }
}

sealed class LoginState {
    object EnteringInfo : LoginState()
    object SubmittingInfo : LoginState()
    object FinishedSuccessfully : LoginState()
}