package com.github.tehras.peloton.workout.details

import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.tehras.peloton.R
import com.github.tehras.peloton.home.Avatar
import com.github.tehras.peloton.utils.toDate

@Composable
fun WorkoutDetailsHeader(workout: WorkoutData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
    ) {
        ConstraintLayout(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            val (avatar, title, workoutName, workoutTime, _, workValue, divider, time_at) = createRefs()

            Avatar(url = workout.workoutDetails.ride.image_url,
                modifier = Modifier.constrainAs(avatar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(divider.top)
                }
            )
            Text(
                text = workout.workoutDetails.ride.title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(avatar.end)
                        end.linkTo(workValue.start)
                        bottom.linkTo(workoutName.top)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 8.dp)
            )
            Text(
                text = stringResource(
                    id = R.string.workout_instructor_name,
                    workout.instructor?.name ?: "",
                    workout.workoutDetails.ride.fitness_discipline_display_name
                ),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .constrainAs(workoutName) {
                        top.linkTo(title.bottom)
                        start.linkTo(title.start)
                        bottom.linkTo(workoutTime.top)
                    }
                    .padding(horizontal = 8.dp)
            )
            Text(
                text = stringResource(
                    id = R.string.workout_air_time,
                    workout.workoutDetails.start_time.toDate()
                ),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .constrainAs(workoutTime) {
                        top.linkTo(workoutName.bottom)
                        start.linkTo(title.start)
                        bottom.linkTo(divider.top)
                    }
                    .padding(horizontal = 8.dp)
            )
            Divider(modifier = Modifier
                .constrainAs(divider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(time_at.top)
                }
                .padding(vertical = 4.dp))
            Text(
                text = workout.workoutDetails.start_time.toDate(),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .constrainAs(time_at) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(horizontal = 4.dp, vertical = 4.dp),
            )
        }
    }
}