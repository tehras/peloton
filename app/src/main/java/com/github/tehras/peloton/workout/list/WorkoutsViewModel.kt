package com.github.tehras.peloton.workout.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.client.ResultWrapper
import com.github.tehras.data.client.safeApiCall
import com.github.tehras.data.data.InstructorResponse
import com.github.tehras.data.data.WorkoutsResponse
import com.github.tehras.data.instructor.InstructorRepo
import com.github.tehras.data.workout.WorkoutRepo
import com.github.tehras.peloton.workout.list.WorkoutDisplayData.Companion.toDisplay
import com.github.tehras.peloton.workout.list.WorkoutsState.Loading
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class WorkoutsViewModel(
    private val workoutRepo: WorkoutRepo,
    private val instructorRepo: InstructorRepo
) : ViewModel() {
    val workoutsState = MutableStateFlow<WorkoutsState>(Loading)

    fun fetchWorkouts(userId: String, workoutType: String?) {
        viewModelScope.launch {
            workoutsState.emit(Loading)

            val workoutsResponse = async { safeApiCall { workoutRepo.fetchWorkouts(userId) } }
            val instructorsResponse = async { safeApiCall { instructorRepo.instructors() } }

            workoutsState.emit(
                handleOutput(
                    workoutType,
                    workoutsResponse.await(),
                    instructorsResponse.await()
                )
            )
        }
    }

    private fun handleOutput(
        workoutType: String?,
        workoutsResult: ResultWrapper<WorkoutsResponse>,
        instructorResponse: ResultWrapper<InstructorResponse>
    ): WorkoutsState {
        if (workoutsResult !is ResultWrapper.Success<WorkoutsResponse>) {
            return WorkoutsState.Error(workoutsResult.asErrorMessage())
        }
        if (instructorResponse !is ResultWrapper.Success<InstructorResponse>) {
            return WorkoutsState.Error(instructorResponse.asErrorMessage())
        }

        return WorkoutsState.Success(
            totalCount = workoutsResult.value.count,
            workouts = workoutsResult.value.workouts
                .filter { workout ->
                    val doesWorkoutTypeMatch = workoutType?.let {
                        workout.fitness_discipline == it.toLowerCase(Locale.ROOT)
                    } ?: true

                    workout.details != null && doesWorkoutTypeMatch
                }
                .map {
                    it.toDisplay(instructors = instructorResponse.value.instructors)
                }
        )
    }
}

sealed class WorkoutsState {
    object Loading : WorkoutsState()
    data class Success(
        val totalCount: Int,
        val workouts: List<WorkoutDisplayData>
    ) : WorkoutsState()

    data class Error(
        val message: String
    ) : WorkoutsState()
}