package com.github.tehras.peloton.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tehras.data.data.InstructorResponse
import com.github.tehras.data.data.WorkoutsResponse
import com.github.tehras.data.instructor.InstructorRepo
import com.github.tehras.data.workout.WorkoutRepo
import com.github.tehras.peloton.workout.WorkoutDisplayData.Companion.toDisplay
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class WorkoutsViewModel(
    private val workoutRepo: WorkoutRepo,
    private val instructorRepo: InstructorRepo
) : ViewModel() {
    private val workouts = MutableSharedFlow<Pair<WorkoutsResponse, InstructorResponse>>()

    val workoutsState
        get() = workouts.transform<Pair<WorkoutsResponse, InstructorResponse>, WorkoutsState> { (workoutsResponse, instructorResponse) ->
            emit(
                WorkoutsState.Success(
                    totalCount = workoutsResponse.count,
                    workouts = workoutsResponse.workouts.map { it.toDisplay(instructors = instructorResponse.instructors) }
                )
            )
        }

    fun fetchAllWorkouts(userId: String) {
        viewModelScope.launch {
            val workoutsResponse = async { workoutRepo.fetchWorkouts(userId) }
            val instructorsResponse = async { instructorRepo.instructors() }

            workouts.emit(workoutsResponse.await() to instructorsResponse.await())
        }
    }
}

sealed class WorkoutsState {
    object Loading : WorkoutsState()
    data class Success(
        val totalCount: Int,
        val workouts: List<WorkoutDisplayData>
    ) : WorkoutsState()
}