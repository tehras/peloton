package com.github.tehras.peloton.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomDrawerLayout
import androidx.compose.material.BottomDrawerValue.Closed
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.github.tehras.peloton.followers.list.FollowersListScreen
import com.github.tehras.peloton.home.HomeBottomSheetState.Empty
import com.github.tehras.peloton.home.HomeBottomSheetState.Followers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun HomeDataScreen(homeState: HomeState.Success) {
    val homeBottomSheetState = remember { mutableStateOf<HomeBottomSheetState>(Empty) }
    val drawerState = rememberBottomDrawerState(Closed) { newState ->
        when (newState) {
            Closed -> homeBottomSheetState.value = Empty
            else -> Unit
        }

        true
    }

    BottomDrawerLayout(
        drawerState = drawerState,
        bodyContent = {
            Column {
                HeaderArea(
                    data = homeState.userData,
                    followersClicked = { homeBottomSheetState.value = Followers }
                )
            }
        },
        drawerContent = {
            when (homeBottomSheetState.value) {
                Empty -> if (!drawerState.isClosed) drawerState.close()
                Followers -> {
                    drawerState.open()
                    FollowersListScreen()
                }
            }
        }
    )
}

private sealed class HomeBottomSheetState {
    object Empty : HomeBottomSheetState()
    object Followers : HomeBottomSheetState()
}