package com.github.tehras.peloton.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.data.User
import com.github.tehras.data.user.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val userRepo: UserRepo
) : ViewModel() {
    val userState = MutableStateFlow<OverviewState>(OverviewState.Loading)

    fun fetchData(userId: String) {
        viewModelScope.launch {
            userState.emit(OverviewState.Success(userRepo.fetchData(userId)))
        }
    }
}

sealed class OverviewState {
    object Loading : OverviewState()
    data class Success(
        val user: User
    ) : OverviewState()
}