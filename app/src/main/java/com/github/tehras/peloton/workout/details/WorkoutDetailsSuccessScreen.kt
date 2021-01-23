package com.github.tehras.peloton.workout.details

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.runtime.Composable

@Composable
fun WorkoutDetailsSuccessScreen(
    workout: WorkoutData
) {
    ScrollableColumn {
        WorkoutDetailsHeader(workout = workout)
        WorkoutLeaderboardAndAchievements(workout = workout)
        WorkoutDetailsOutputs(workout = workout)
        WorkoutDetailsDescription(workout = workout)
        WorkoutDetailsPerformance(workout = workout)
    }
}

