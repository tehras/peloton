package com.github.tehras.peloton.user

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.CalendarResponse
import com.github.tehras.data.data.User
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.followers.list.FollowersListScreen
import com.github.tehras.peloton.followers.list.FollowingListScreen
import com.github.tehras.peloton.home.CalendarArea
import com.github.tehras.peloton.home.HeaderArea
import com.github.tehras.peloton.home.WorkoutArea
import com.github.tehras.peloton.user.HomeBottomSheetState.*
import com.github.tehras.peloton.user.SlideUpSheetState.SlideUpSheetValue
import com.github.tehras.peloton.workout.list.Workout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalMaterialApi
@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun UserScreen(
  user: User,
  calendarResponse: CalendarResponse?,
  navigateTo: (Screen) -> Unit
) {
  val homeBottomSheetState = remember { mutableStateOf<HomeBottomSheetState>(Empty) }

  val drawerState = rememberBottomSheetState(Collapsed) { newState ->
    when (newState) {
      Collapsed -> homeBottomSheetState.value = Empty
      Expanded -> Unit // do nothing.
    }

    true
  }
  val sheetState = rememberSlideUpSheetState(SlideUpSheetValue.Collapsed) { newState ->
    when (newState) {
      SlideUpSheetValue.Collapsed -> homeBottomSheetState.value = Empty
      SlideUpSheetValue.Expanded -> Unit
    }

    true
  }
  rememberBottomSheetScaffoldState(
    bottomSheetState = drawerState
  )

  SlideUpScreen(
    sheetContent = {
      when (homeBottomSheetState.value) {
        Followers -> FollowersListScreen(user.id, navigateTo)
        Following -> FollowingListScreen(user.id, navigateTo)
        Empty -> Unit // Leave empty
      }
    },
    sheetState = sheetState,
    sheetGesturesEnabled = homeBottomSheetState.value != Empty
  ) {
    BodyContent(user, calendarResponse, homeBottomSheetState, navigateTo)
  }
  // BottomSheetScaffold(
  //     scaffoldState = scaffoldState,
  //     sheetGesturesEnabled = homeBottomSheetState.value != Empty,
  //     sheetPeekHeight = 0.dp,
  //     sheetContent = {
  //         when (homeBottomSheetState.value) {
  //             Followers -> FollowersListScreen(user.id, navigateTo)
  //             Following -> FollowingListScreen(user.id, navigateTo)
  //             Empty -> Unit // Leave empty
  //         }
  //     }
  // ) {
  //   BodyContent(user, calendarResponse, homeBottomSheetState, navigateTo)
  // }
}

@Composable
private fun BodyContent(
  user: User,
  calendarResponse: CalendarResponse?,
  homeBottomSheetState: MutableState<HomeBottomSheetState>,
  navigateTo: (Screen) -> Unit
) {
  Column {
    HomeContent(user, calendarResponse, homeBottomSheetState, navigateTo)
  }
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