package com.github.tehras.peloton.workout

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.LoadingScreen
import com.github.tehras.peloton.workout.WorkoutDetailsState.Loading
import com.github.tehras.peloton.workout.WorkoutDetailsState.Success
import kotlinx.android.parcel.Parcelize
import org.koin.androidx.compose.inject

@Composable
fun WorkoutDetailsScreen(workoutId: String) {
    val viewModel: WorkoutDetailsViewModel by inject()

    val state: State<WorkoutDetailsState> =
        viewModel.workoutDetailsState.collectAsState(initial = Loading)

    when (state.value) {
        Loading -> {
            viewModel.fetchWorkoutDetails(workoutId = workoutId)
            LoadingScreen()
        }
        is Success -> Text("Success")
    }
}

@Parcelize
data class WorkoutDetails(private val workoutId: String) : Screen {
    override val isTopScreen: Boolean
        get() = false

    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        WorkoutDetailsScreen(workoutId)
    }
}