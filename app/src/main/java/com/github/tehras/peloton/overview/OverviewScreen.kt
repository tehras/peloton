package com.github.tehras.peloton.overview

import androidx.compose.material.ExperimentalMaterialApi
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

@ExperimentalMaterialApi
@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun OverviewScreen(
    userId: String,
    navigateTo: (Screen) -> Unit
) {
    val viewModel: OverviewViewModel = getViewModel()

    val state: State<OverviewState> = viewModel.overviewState.asFlow()
        .collectAsState(initial = OverviewState.Loading)

    when (val data = state.value) {
        OverviewState.Loading -> {
            LoadingScreen()
            viewModel.fetchData(userId = userId)
        }
        is OverviewState.Success -> UserScreen(data.userData, data.calendarData, navigateTo)
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