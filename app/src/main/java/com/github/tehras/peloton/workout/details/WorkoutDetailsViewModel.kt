package com.github.tehras.peloton.workout.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                workoutRepo.fetchWorkoutDetails(workoutId)
            }
            val workoutPerformance = async {
                workoutRepo.fetchWorkoutPerformance(workoutId)
            }
            val instructorsResponse = async {
                instructorRepo.instructors()
            }

            workoutDetails.emit(
                Success(
                    combineResponses(
                        workoutsResponse.await(),
                        workoutPerformance.await(),
                        instructorsResponse.await()
                    )
                )
            )
        }
    }

    private fun combineResponses(
        workoutDetails: WorkoutDetailsResponse,
        workoutPerformance: WorkoutDetailsPerformanceResponse,
        instructors: InstructorResponse
    ): WorkoutData {
        return WorkoutData(
            workoutDetails = workoutDetails,
            workoutPerformance = workoutPerformance,
            instructor = instructors.instructors.firstOrNull { it.id == workoutDetails.ride.instructor_id }
        )
    }
}

sealed class WorkoutDetailsState {
    object Loading : WorkoutDetailsState()
    data class Success(val workoutData: WorkoutData) : WorkoutDetailsState()
}

data class WorkoutData(
    val workoutDetails: WorkoutDetailsResponse,
    val workoutPerformance: WorkoutDetailsPerformanceResponse,
    val instructor: Instructor?
)