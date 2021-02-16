package com.github.tehras.peloton.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.LoadingScreen
import com.github.tehras.peloton.user.UserScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.parcelize.Parcelize
import com.github.tehras.peloton.utils.getViewModel

@ExperimentalMaterialApi
@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(
  navigateTo: (Screen) -> Unit,
  homeViewModel: HomeViewModel = getViewModel()
) {
  LaunchedEffect(Unit) {
    homeViewModel.fetchData()
  }

  val state: State<HomeState> = homeViewModel.homeDataFlow
    .collectAsState()

  when (val data = state.value) {
    HomeState.Loading -> LoadingScreen()
    is HomeState.Success -> UserScreen(data.userData, data.calendarData, navigateTo)
  }
}

@Parcelize
object Home : Screen {
  @ExperimentalMaterialApi
  @FlowPreview
  @ExperimentalCoroutinesApi
  @Composable
  override fun Compose(navigateTo: (Screen) -> Unit) {
    HomeScreen(navigateTo)
  }
}
