package com.github.tehras.peloton.workout.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.ErrorScreen
import com.github.tehras.peloton.shared.LoadingScreen
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.inject

@ExperimentalCoroutinesApi
@Composable
fun WorkoutScreen(userId: String, navigateTo: (Screen) -> Unit) {
    val workoutsViewModel: WorkoutsViewModel by inject()

    val state: State<WorkoutsState> = workoutsViewModel.workoutsState
        .collectAsState()

    when (val data = state.value) {
        WorkoutsState.Loading -> {
            LoadingScreen()
            workoutsViewModel.fetchAllWorkouts(userId = userId)
        }
        is WorkoutsState.Success -> WorkoutDataScreen(data, navigateTo)
        is WorkoutsState.Error -> ErrorScreen(data.message) {
            workoutsViewModel.fetchAllWorkouts(userId = userId)
        }
    }
}

@Parcelize
data class Workout(private val userId: String) : Screen {
    override val isTopScreen: Boolean
        get() = false

    @ExperimentalCoroutinesApi
    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        WorkoutScreen(userId, navigateTo)
    }
}