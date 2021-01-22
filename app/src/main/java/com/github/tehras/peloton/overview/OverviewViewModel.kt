package com.github.tehras.peloton.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.data.CalendarResponse
import com.github.tehras.data.data.User
import com.github.tehras.data.overview.OverviewRepo
import com.github.tehras.peloton.overview.OverviewState.Loading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class OverviewViewModel(
  private val overviewRepo: OverviewRepo
) : ViewModel() {
  val overviewState = MutableStateFlow<OverviewState>(Loading)

  fun fetchData(userId: String) {
    viewModelScope.launch {
      overviewState.emit(Loading)
      
      val userData = overviewRepo.fetchData(userId = userId)
      val calendarData = overviewRepo.fetchCalendar(userId = userId)

      overviewState.emit(
        OverviewState.Success(
          userData = userData,
          calendarData = calendarData
        )
      )
    }
  }
}

sealed class OverviewState {
  object Loading : OverviewState()
  data class Success(
    val userData: User,
    val calendarData: CalendarResponse
  ) : OverviewState()
}