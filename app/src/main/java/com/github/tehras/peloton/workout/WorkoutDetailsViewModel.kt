package com.github.tehras.peloton.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.data.InstructorResponse
import com.github.tehras.data.data.WorkoutDetailsResponse
import com.github.tehras.data.instructor.InstructorRepo
import com.github.tehras.data.workout.WorkoutRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel(
    private val workoutRepo: WorkoutRepo,
    private val instructorRepo: InstructorRepo
) : ViewModel() {
    private val workoutDetails =
        MutableSharedFlow<Pair<WorkoutDetailsResponse, InstructorResponse>>()

    val workoutDetailsState
        get() = workoutDetails.transform<Pair<WorkoutDetailsResponse, InstructorResponse>, WorkoutDetailsState> { (workoutDetails, _) ->
            emit(
                WorkoutDetailsState.Success(
                    workoutDetails = workoutDetails
                )
            )
        }

    fun fetchWorkoutDetails(workoutId: String) {
        viewModelScope.launch {
            val workoutsResponse = async { workoutRepo.fetchWorkoutDetails(workoutId) }
            val instructorsResponse = async { instructorRepo.instructors() }

            workoutDetails.emit(workoutsResponse.await() to instructorsResponse.await())
        }
    }
}

sealed class WorkoutDetailsState {
    object Loading : WorkoutDetailsState()
    data class Success(
        val workoutDetails: WorkoutDetailsResponse
    ) : WorkoutDetailsState()
}