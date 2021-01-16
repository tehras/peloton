package com.github.tehras.peloton.workout.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.ErrorScreen
import com.github.tehras.peloton.shared.LoadingScreen
import com.github.tehras.peloton.workout.details.WorkoutDetailsState.Loading
import com.github.tehras.peloton.workout.details.WorkoutDetailsState.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel

@ExperimentalCoroutinesApi
@Composable
fun WorkoutDetailsScreen(workoutId: String) {
    val viewModel: WorkoutDetailsViewModel = getViewModel()

    val state: State<WorkoutDetailsState> =
        viewModel.workoutDetails.collectAsState()

    when (val data = state.value) {
        Loading -> {
            viewModel.fetchWorkoutDetails(workoutId = workoutId)
            LoadingScreen()
        }
        is Success -> WorkoutDetailsSuccessScreen(data.workoutData)
        is WorkoutDetailsState.Error -> ErrorScreen(message = data.message) {
            viewModel.fetchWorkoutDetails(workoutId = workoutId)
        }
    }
}

@Parcelize
data class WorkoutDetails(private val workoutId: String) : Screen {
    override val isTopScreen: Boolean
        get() = false

    @ExperimentalCoroutinesApi
    @Composable
    override fun Compose(navigateTo: (Screen) -> Unit) {
        WorkoutDetailsScreen(workoutId)
    }
}