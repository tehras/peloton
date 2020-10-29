package com.github.tehras.peloton.init

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.auth.AuthRepo
import com.github.tehras.peloton.init.InitState.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class InitViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {
    val onEvent = MutableStateFlow<InitState>(Initializing)

    init {
        getStatus()
    }

    private fun getStatus() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Add an artificial delay.
                delay(500)
                onEvent.value = if (authRepo.isLoggedIn()) {
                    UserDataReady
                } else {
                    UserDataNotFound
                }
            }
        }
    }
}

sealed class InitState {
    object Initializing : InitState()
    object UserDataNotFound : InitState()
    object UserDataReady : InitState()
}