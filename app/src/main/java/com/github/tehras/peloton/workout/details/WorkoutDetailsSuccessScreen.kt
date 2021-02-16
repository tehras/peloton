package com.github.tehras.peloton.workout.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WorkoutDetailsSuccessScreen(
  workout: WorkoutData
) {
  Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
    WorkoutDetailsHeader(workout = workout)
    WorkoutLeaderboardAndAchievements(workout = workout)
    WorkoutDetailsOutputs(workout = workout)
    WorkoutDetailsDescription(workout = workout)
    WorkoutDetailsPerformance(workout = workout)
  }
}

