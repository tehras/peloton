package com.github.tehras.peloton.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.LoadingScreen
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import org.koin.androidx.compose.inject

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel by inject()

    val state: State<HomeState> = homeViewModel.homeDataFlow.asFlow()
        .collectAsState(initial = HomeState.Loading)

    when (val data = state.value) {
        HomeState.Loading -> LoadingScreen()
        is HomeState.Success -> HomeDataScreen(data)
    }
}

@Parcelize
object Home : Screen {
    @FlowPreview
    @ExperimentalCoroutinesApi
    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        HomeScreen()
    }
}
