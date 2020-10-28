package com.github.tehras.peloton.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.data.User
import com.github.tehras.data.overview.UserRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel(
    private val userRepo: UserRepo
) : ViewModel() {
    val homeDataFlow = ConflatedBroadcastChannel<HomeData>()

    init {
        viewModelScope.launch {
            val result = userRepo.fetchData()

            homeDataFlow.send(HomeData.Success(userData = result))
        }
    }
}

sealed class HomeData {
    object Loading : HomeData()
    data class Success(
        val userData: User
    ) : HomeData()
}