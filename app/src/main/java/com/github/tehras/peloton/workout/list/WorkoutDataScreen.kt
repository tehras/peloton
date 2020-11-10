package com.github.tehras.peloton.workout.list

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.github.tehras.peloton.R
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.home.Avatar
import com.github.tehras.peloton.workout.details.WorkoutDetails

@Composable
fun WorkoutDataScreen(workoutsData: WorkoutsState.Success, navigateTo: (Screen) -> Unit) {
    Column {
        LazyColumnForIndexed(items = workoutsData.workouts) { index, workout ->
            if (index == 0) {
                Surface(contentColor = contentColorFor(color = MaterialTheme.colors.surface)) {
                    Text(
                        text = stringResource(
                            id = R.string.workout_list_title,
                            workoutsData.workouts.count()
                        ),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                    )
                }
            }
            WorkoutItem(
                workout = workout,
                workoutSelected = { navigateTo(WorkoutDetails(workoutId = workout.workoutId)) }
            )
        }
    }
}

@Composable
fun WorkoutItem(workout: WorkoutDisplayData, workoutSelected: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .clickable(onClick = workoutSelected)
    ) {
        ConstraintLayout(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            val (avatar, title, workoutName, workoutTime, _, workValue, workLabel, divider, time_at) = createRefs()

            Avatar(url = workout.image,
                modifier = Modifier.constrainAs(avatar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(divider.top)
                }
            )
            Text(
                text = workout.workoutName,
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
                    workout.instructorName ?: "",
                    workout.workoutType
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
                text = stringResource(id = R.string.workout_air_time, workout.scheduleDate),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .constrainAs(workoutTime) {
                        top.linkTo(workoutName.bottom)
                        start.linkTo(title.start)
                        bottom.linkTo(divider.top)
                    }
                    .padding(horizontal = 8.dp)
            )
            Text(
                text = workout.workoutEnergy,
                style = MaterialTheme.typography.h6.applyPB(workout),
                modifier = Modifier
                    .constrainAs(workValue) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(workLabel.top)
                    }
            )
            Text(
                text = stringResource(id = R.string.workout_work_value),
                style = MaterialTheme.typography.body1.applyPB(workout),
                modifier = Modifier.constrainAs(workLabel) {
                    top.linkTo(workValue.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(divider.top)
                }
            )
            Divider(modifier = Modifier
                .constrainAs(divider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(time_at.top)
                }
                .padding(vertical = 4.dp))
            Text(
                text = workout.workoutDate,
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

@Composable
private fun TextStyle.applyPB(workout: WorkoutDisplayData): TextStyle {
    return if (workout.isPersonalBest) {
        copy(color = MaterialTheme.colors.secondary)
    } else {
        this
    }
}
