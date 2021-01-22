package com.github.tehras.peloton.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.onCommit
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.LoadingScreen
import com.github.tehras.peloton.user.UserScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(
  navigateTo: (Screen) -> Unit,
  homeViewModel: HomeViewModel = getViewModel()
) {
  onCommit { homeViewModel.fetchData() }

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
