package com.github.tehras.peloton.overview

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.overview.OverviewState.Loading
import com.github.tehras.peloton.overview.OverviewState.Success
import com.github.tehras.peloton.shared.LoadingScreen
import com.github.tehras.peloton.user.UserScreen
import com.github.tehras.peloton.utils.getViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.parcelize.Parcelize

@ExperimentalMaterialApi
@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun OverviewScreen(
  userId: String,
  navigateTo: (Screen) -> Unit,
  viewModel: OverviewViewModel = getViewModel()
) {
  LaunchedEffect(userId) {
    viewModel.fetchData(userId = userId)
  }

  val state: State<OverviewState> = viewModel.overviewState
    .collectAsState(initial = Loading)

  when (val data = state.value) {
    Loading -> LoadingScreen()
    is Success -> UserScreen(data.userData, data.calendarData, navigateTo)
  }
}

@Parcelize
data class Overview(val userId: String) : Screen {
  override val isTopScreen: Boolean
    get() = false

  @ExperimentalMaterialApi
  @ExperimentalCoroutinesApi
  @FlowPreview
  @Composable
  override fun Compose(navigateTo: (Screen) -> Unit) {
    OverviewScreen(userId, navigateTo)
  }
}