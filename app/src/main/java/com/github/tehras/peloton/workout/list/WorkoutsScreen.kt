package com.github.tehras.peloton.workout.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.shared.LoadingScreen
import kotlinx.android.parcel.Parcelize
import org.koin.androidx.compose.inject

@Composable
fun WorkoutScreen(userId: String, navigateTo: (Screen) -> Unit) {
    val workoutsViewModel: WorkoutsViewModel by inject()

    val state: State<WorkoutsState> = workoutsViewModel.workoutsState
        .collectAsState(initial = WorkoutsState.Loading)

    when (val data = state.value) {
        WorkoutsState.Loading -> {
            LoadingScreen()
            workoutsViewModel.fetchAllWorkouts(userId)
        }
        is WorkoutsState.Success -> WorkoutDataScreen(data, navigateTo)
    }
}

@Parcelize
data class Workout(private val userId: String) : Screen {
    override val isTopScreen: Boolean
        get() = false

    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        WorkoutScreen(userId, navigateTo)
    }
}