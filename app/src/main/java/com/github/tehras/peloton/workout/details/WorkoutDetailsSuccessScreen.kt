package com.github.tehras.peloton.workout.details

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.runtime.Composable

@ExperimentalLazyDsl
@Composable
fun WorkoutDetailsSuccessScreen(
    workout: WorkoutData
) {
    ScrollableColumn {
        WorkoutDetailsHeader(workout = workout)
        WorkoutLeaderboardAndAchievements(workout = workout)
        WorkoutDetailsOutputs(workout = workout)
    }
}

