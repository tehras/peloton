package com.github.tehras.peloton.home

import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.LoadingScreen
import com.github.tehras.peloton.user.UserScreen
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import org.koin.androidx.compose.inject

@ExperimentalLazyDsl
@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun HomeScreen(navigateTo: (Screen) -> Unit) {
    val homeViewModel: HomeViewModel by inject()

    val state: State<HomeState> = homeViewModel.homeDataFlow.asFlow()
        .collectAsState(initial = HomeState.Loading)

    when (val data = state.value) {
        HomeState.Loading -> LoadingScreen()
        is HomeState.Success -> UserScreen(data.userData, data.calendarData, navigateTo)
    }
}

@Parcelize
object Home : Screen {
    @ExperimentalLazyDsl
    @FlowPreview
    @ExperimentalCoroutinesApi
    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        HomeScreen(navigateTo)
    }
}
