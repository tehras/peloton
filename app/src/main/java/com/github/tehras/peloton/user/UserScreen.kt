package com.github.tehras.peloton.user

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomDrawerLayout
import androidx.compose.material.BottomDrawerValue.Closed
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.github.tehras.data.data.CalendarResponse
import com.github.tehras.data.data.User
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.followers.list.FollowersListScreen
import com.github.tehras.peloton.followers.list.FollowingListScreen
import com.github.tehras.peloton.home.CalendarArea
import com.github.tehras.peloton.home.HeaderArea
import com.github.tehras.peloton.home.WorkoutArea
import com.github.tehras.peloton.user.HomeBottomSheetState.*
import com.github.tehras.peloton.workout.list.Workout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun UserScreen(
    user: User,
    calendarResponse: CalendarResponse?,
    navigateTo: (Screen) -> Unit
) {
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
        gesturesEnabled = homeBottomSheetState.value !is Empty,
        bodyContent = {
            Column {
                HomeContent(user, calendarResponse, homeBottomSheetState, navigateTo)
            }
        },
        drawerContent = {
            when (homeBottomSheetState.value) {
                Empty -> if (!drawerState.isClosed) drawerState.close()
                Followers -> {
                    drawerState.open()
                    FollowersListScreen(user.id, navigateTo)
                }
                Following -> {
                    drawerState.open()
                    FollowingListScreen(user.id, navigateTo)
                }
            }
        }
    )
}

@Composable
private fun HomeContent(
    userData: User,
    calendarResponse: CalendarResponse?,
    homeBottomSheetState: MutableState<HomeBottomSheetState>,
    navigateTo: (Screen) -> Unit
) {
    ScrollableColumn {
        HeaderArea(
            data = userData,
            followersClicked = {
                homeBottomSheetState.value = Followers
            },
            followingClicked = { homeBottomSheetState.value = Following }
        )
        WorkoutArea(
            data = userData,
            allWorkoutsClicked = { navigateTo(Workout(userData.id)) },
            workoutClicked = { navigateTo(Workout(userData.id, it)) }
        )
        if (calendarResponse != null) {
            CalendarArea(
                calendarData = calendarResponse
            )
        }
    }
}

private sealed class HomeBottomSheetState {
    object Empty : HomeBottomSheetState()
    object Followers : HomeBottomSheetState()
    object Following : HomeBottomSheetState()
}