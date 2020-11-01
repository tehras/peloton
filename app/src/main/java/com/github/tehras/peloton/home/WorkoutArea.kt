package com.github.tehras.peloton.home

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.User
import com.github.tehras.peloton.R
import com.github.tehras.peloton.common.CoilImage
import com.github.tehras.peloton.common.LazyGridFor

@Composable
fun WorkoutArea(
    data: User,
    allWorkoutsClicked: () -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
            .padding(horizontal = 12.dp)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.workout_title, data.total_workouts),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                )
            )
            Divider()
            val comparator = compareByDescending<User.Workout> { it.count }
                .thenBy { it.name }
            LazyGridFor(
                data = data.workouts
                    .sortedWith(comparator),
                rowSize = 3
            ) { workout ->
                WorkoutItem(workout = workout)
            }
            Divider()
            Text(
                text = stringResource(id = R.string.workout_view_all),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .clickable(onClick = allWorkoutsClicked)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 12.dp
                    )
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun WorkoutItem(workout: User.Workout) {
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier.padding(8.dp)
            .fillMaxSize()
    ) {
        val nameFontWeight = if (workout.count == 0) {
            FontWeight.Light
        } else {
            FontWeight.Normal
        }
        CoilImage(
            data = workout.icon_url,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter(
                color = MaterialTheme.colors.secondary,
                blendMode = BlendMode.SrcAtop
            )
        )
        Text(
            text = workout.name,
            style = MaterialTheme.typography.caption,
            fontWeight = nameFontWeight
        )
        Text(
            text = workout.count.toString(),
            style = MaterialTheme.typography.body2,
            fontWeight = nameFontWeight
        )
    }
}
