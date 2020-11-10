package com.github.tehras.peloton.workout.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.client.ResultWrapper
import com.github.tehras.data.client.safeApiCall
import com.github.tehras.data.data.Instructor
import com.github.tehras.data.data.InstructorResponse
import com.github.tehras.data.data.WorkoutDetailsPerformanceResponse
import com.github.tehras.data.data.WorkoutDetailsResponse
import com.github.tehras.data.instructor.InstructorRepo
import com.github.tehras.data.workout.WorkoutRepo
import com.github.tehras.peloton.workout.details.WorkoutDetailsState.Loading
import com.github.tehras.peloton.workout.details.WorkoutDetailsState.Success
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorkoutDetailsViewModel(
    private val workoutRepo: WorkoutRepo,
    private val instructorRepo: InstructorRepo
) : ViewModel() {
    val workoutDetails = MutableStateFlow<WorkoutDetailsState>(Loading)

    fun fetchWorkoutDetails(workoutId: String) {
        viewModelScope.launch {
            val workoutsResponse = async {
                safeApiCall { workoutRepo.fetchWorkoutDetails(workoutId) }
            }
            val workoutPerformance = async {
                safeApiCall { workoutRepo.fetchWorkoutPerformance(workoutId) }
            }
            val instructorsResponse = async {
                safeApiCall { instructorRepo.instructors() }
            }

            workoutDetails.emit(
                combineResponses(
                    workoutsResponse.await(),
                    workoutPerformance.await(),
                    instructorsResponse.await()
                )
            )
        }
    }

    private fun combineResponses(
        workoutDetails: ResultWrapper<WorkoutDetailsResponse>,
        workoutPerformance: ResultWrapper<WorkoutDetailsPerformanceResponse>,
        instructors: ResultWrapper<InstructorResponse>
    ): WorkoutDetailsState {
        if (workoutDetails !is ResultWrapper.Success<WorkoutDetailsResponse>) {
            return WorkoutDetailsState.Error(message = workoutDetails.asErrorMessage())
        }
        if (workoutPerformance !is ResultWrapper.Success<WorkoutDetailsPerformanceResponse>) {
            return WorkoutDetailsState.Error(message = workoutPerformance.asErrorMessage())
        }
        if (instructors !is ResultWrapper.Success<InstructorResponse>) {
            return WorkoutDetailsState.Error(message = instructors.asErrorMessage())
        }

        val workoutData = WorkoutData(
            workoutDetails = workoutDetails.value,
            workoutPerformance = workoutPerformance.value,
            instructor = instructors.value.instructors.firstOrNull {
                it.id == workoutDetails.value.ride.instructor_id
            }
        )

        return Success(workoutData = workoutData)
    }
}

sealed class WorkoutDetailsState {
    object Loading : WorkoutDetailsState()
    data class Success(val workoutData: WorkoutData) : WorkoutDetailsState()
    data class Error(val message: String) : WorkoutDetailsState()
}

data class WorkoutData(
    val workoutDetails: WorkoutDetailsResponse,
    val workoutPerformance: WorkoutDetailsPerformanceResponse,
    val instructor: Instructor?
)