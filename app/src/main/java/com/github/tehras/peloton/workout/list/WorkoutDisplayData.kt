package com.github.tehras.peloton.workout.list

import com.github.tehras.data.data.Instructor
import com.github.tehras.data.data.Ride
import com.github.tehras.data.data.Workout
import com.github.tehras.peloton.utils.formatWork
import com.github.tehras.peloton.utils.toDate

/**
 * Data converted from [Workout] and [Instructor] list.
 */
data class WorkoutDisplayData(
  val workoutId: String,
  val instructorId: String?,
  val instructorName: String?,
  val workoutType: String,
  val image: String,
  val isPersonalBest: Boolean,
  val workoutName: String,
  val workoutEnergy: String,
  val workoutDate: String,
  val scheduleDate: String
) {
  companion object {
    fun Workout.toDisplay(instructors: List<Instructor>): WorkoutDisplayData {
      val ride = details?.ride ?: error("details cannot be empty")

      return WorkoutDisplayData(
        workoutId = id,
        instructorId = ride.instructor_id,
        instructorName = instructors.findInstructor(ride),
        image = ride.image_url,
        workoutName = ride.title,
        workoutEnergy = total_work.formatWork(),
        workoutDate = start_time.toDate(),
        scheduleDate = ride.scheduled_start_time.toDate(),
        workoutType = ride.fitness_discipline_display_name,
        isPersonalBest = is_total_work_personal_record
      )
    }
  }
}

private fun List<Instructor>.findInstructor(ride: Ride): String? {
  if (ride.instructor_id == null) return null

  val instructor = this.firstOrNull { ride.instructor_id == it.id }

  return instructor?.name
}