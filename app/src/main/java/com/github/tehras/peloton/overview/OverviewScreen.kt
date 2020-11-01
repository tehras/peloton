package com.github.tehras.peloton.overview

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
import org.koin.androidx.compose.inject

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalLazyDsl
@Composable
fun OverviewScreen(
    userId: String,
    navigateTo: (Screen) -> Unit
) {
    val viewModel: OverviewViewModel by inject()

    val state: State<OverviewState> = viewModel.userState
        .collectAsState()

    when (val data = state.value) {
        OverviewState.Loading -> {
            LoadingScreen()
            viewModel.fetchData(userId = userId)
        }
        is OverviewState.Success -> UserScreen(data.user, navigateTo)
    }
}

@Parcelize
data class Overview(val userId: String) : Screen {
    override val isTopScreen: Boolean
        get() = false

    @ExperimentalCoroutinesApi
    @FlowPreview
    @ExperimentalLazyDsl
    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        OverviewScreen(userId, navigateTo)
    }
}