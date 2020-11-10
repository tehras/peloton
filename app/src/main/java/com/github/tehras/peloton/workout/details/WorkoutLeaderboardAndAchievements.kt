package com.github.tehras.peloton.workout.details

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.Achievement
import com.github.tehras.peloton.R
import com.github.tehras.peloton.common.CoilImage
import com.github.tehras.peloton.utils.formatDecimal

@ExperimentalLazyDsl
@Composable
fun WorkoutLeaderboardAndAchievements(workout: WorkoutData) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Leaderboard(workout = workout)
        }
        if (workout.workoutDetails.achievements.isNotEmpty()) {
            items(workout.workoutDetails.achievements) { achievement ->
                Achievement(achievement = achievement)
            }
        } else {
            item { EmptyAchievements() }
        }
    }
}

@Composable
fun EmptyAchievements() {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .preferredHeight(height = 80.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.workout_details_achievements_empty),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)
            )
        }
    }
}

@Composable
private fun Achievement(achievement: Achievement) {
    Card(
        modifier = Modifier.preferredHeight(height = 80.dp).padding(start = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                modifier = Modifier.size(24.dp),
                data = achievement.image_url
            )
            Text(
                text = achievement.name,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
private fun Leaderboard(workout: WorkoutData) {
    if (workout.workoutDetails.leaderboard_rank == null) return

    Card(
        modifier = Modifier
            .preferredHeight(height = 80.dp)
            .padding(start = 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${workout.workoutDetails.leaderboard_rank!!.formatDecimal()} ",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = stringResource(
                        id = R.string.workout_details_leaderboard,
                        workout.workoutDetails.total_leaderboard_users.formatDecimal()
                    ),
                    style = MaterialTheme.typography.body2
                )
            }
            Text(
                text = stringResource(id = R.string.workout_details_leaderboard_label),
                style = MaterialTheme.typography.caption
            )
        }
    }
}