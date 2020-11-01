package com.github.tehras.data.workout

import com.github.tehras.data.data.WorkoutDetailsPerformanceResponse
import com.github.tehras.data.data.WorkoutDetailsResponse
import com.github.tehras.data.data.WorkoutsResponse

interface WorkoutRepo {
    suspend fun fetchWorkouts(userId: String): WorkoutsResponse
    suspend fun fetchWorkoutDetails(workoutId: String): WorkoutDetailsResponse
    suspend fun fetchWorkoutPerformance(workoutId: String): WorkoutDetailsPerformanceResponse
}