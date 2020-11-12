package com.github.tehras.peloton.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.data.CalendarResponse
import com.github.tehras.data.data.User
import com.github.tehras.data.overview.OverviewRepo
import com.github.tehras.peloton.home.HomeState.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel(
    private val overviewRepo: OverviewRepo
) : ViewModel() {
    val homeDataFlow = ConflatedBroadcastChannel<HomeState>()

    init {
        viewModelScope.launch {
            val userData = overviewRepo.fetchData()
            val calendarData = overviewRepo.fetchCalendar()

            homeDataFlow.send(
                Success(
                    userData = userData,
                    calendarData = calendarData
                )
            )
        }
    }
}

sealed class HomeState {
    object Loading : HomeState()
    data class Success(
        val userData: User,
        val calendarData: CalendarResponse
    ) : HomeState()
}