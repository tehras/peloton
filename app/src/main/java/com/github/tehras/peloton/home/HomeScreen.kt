package com.github.tehras.peloton.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.LoadingScreen
import com.github.tehras.peloton.user.UserScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(navigateTo: (Screen) -> Unit) {
    val homeViewModel: HomeViewModel = getViewModel()

    val state: State<HomeState> = homeViewModel.homeDataFlow.asFlow()
        .collectAsState(initial = HomeState.Loading)

    when (val data = state.value) {
        HomeState.Loading -> LoadingScreen()
        is HomeState.Success -> UserScreen(data.userData, data.calendarData, navigateTo)
    }
}

@Parcelize
object Home : Screen {
    @FlowPreview
    @ExperimentalCoroutinesApi
    @Composable
    override fun Compose(navigateTo: (Screen) -> Unit) {
        HomeScreen(navigateTo)
    }
}
