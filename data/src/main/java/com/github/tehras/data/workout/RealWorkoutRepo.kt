package com.github.tehras.data.workout

import com.github.tehras.data.PelotonApi
import com.github.tehras.data.data.WorkoutDetailsPerformanceResponse
import com.github.tehras.data.data.WorkoutDetailsResponse
import com.github.tehras.data.data.WorkoutsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealWorkoutRepo(
    private val pelotonApi: PelotonApi
) : WorkoutRepo {
    override suspend fun fetchWorkouts(userId: String): WorkoutsResponse =
        withContext(Dispatchers.IO) {
            pelotonApi.workouts(userId = userId)
        }

    override suspend fun fetchWorkoutDetails(workoutId: String): WorkoutDetailsResponse =
        withContext(Dispatchers.IO) {
            pelotonApi.workoutPerformance(workoutId = workoutId)
            pelotonApi.workout(workoutId = workoutId)
        }

    override suspend fun fetchWorkoutPerformance(workoutId: String): WorkoutDetailsPerformanceResponse =
        withContext(Dispatchers.IO) {
            pelotonApi.workoutPerformance(workoutId = workoutId)
        }
}