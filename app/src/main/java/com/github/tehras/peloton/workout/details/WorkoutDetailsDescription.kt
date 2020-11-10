package com.github.tehras.peloton.workout.details

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.tehras.peloton.R

@Composable
fun WorkoutDetailsDescription(workout: WorkoutData) {
    Card(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.workout_details_description_label),
                style = MaterialTheme.typography.body1
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = workout.workoutDetails.ride.description,
                style = MaterialTheme.typography.caption
            )
        }
    }
}