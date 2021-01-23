package com.github.tehras.data.data

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutDetailsPerformanceResponse(
  val seconds_since_pedaling_start: List<Int>,
  val summaries: List<Summary>,
  val metrics: List<Metric>
)

@Serializable
data class Summary(
  val display_name: String,
  val display_unit: String,
  val value: Double
)

@Serializable
data class Metric(
  val display_name: String,
  val display_unit: String,
  val max_value: Double,
  val average_value: Double,
  val values: List<Double>
)