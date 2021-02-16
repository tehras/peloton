package com.github.tehras.peloton.workout.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.ErrorScreen
import com.github.tehras.peloton.shared.LoadingScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.parcelize.Parcelize
import com.github.tehras.peloton.utils.getViewModel

@ExperimentalCoroutinesApi
@Composable
fun WorkoutScreen(
  userId: String,
  workout: String?,
  navigateTo: (Screen) -> Unit
) {
  val workoutsViewModel: WorkoutsViewModel = getViewModel()

  LaunchedEffect(userId) {
    workoutsViewModel.fetchWorkouts(userId = userId, workoutType = workout)
  }

  val state: State<WorkoutsState> = workoutsViewModel.workoutsState
    .collectAsState()

  when (val data = state.value) {
    WorkoutsState.Loading -> LoadingScreen()
    is WorkoutsState.Success -> WorkoutDataScreen(data, navigateTo)
    is WorkoutsState.Error -> ErrorScreen(data.message) {
      workoutsViewModel.fetchWorkouts(userId = userId, workoutType = workout)
    }
  }
}

@Parcelize
data class Workout(
  private val userId: String,
  private val workout: String? = null
) : Screen {
  override val isTopScreen: Boolean
    get() = false

  @ExperimentalCoroutinesApi
  @Composable
  override fun Compose(navigateTo: (Screen) -> Unit) {
    WorkoutScreen(userId, workout, navigateTo)
  }
}