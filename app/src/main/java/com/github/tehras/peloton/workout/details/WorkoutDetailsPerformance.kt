package com.github.tehras.peloton.workout.details

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.LineChartData.Point
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.NoPointDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.data.data.Metric
import com.github.tehras.peloton.R
import com.github.tehras.peloton.utils.formatDecimal
import kotlin.math.roundToInt

@Composable
fun WorkoutDetailsPerformance(workout: WorkoutData) {
  workout.workoutPerformance.metrics.forEach { metric ->
    WorkoutDetailsGraph(metric, workout.workoutPerformance.seconds_since_pedaling_start)
  }
}

@Composable
fun WorkoutDetailsGraph(metric: Metric, secondsSincePedalingStart: List<Int>) {
  Card(
    modifier = Modifier
      .padding(horizontal = 12.dp, vertical = 4.dp)
      .fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).fillMaxWidth()) {
      Row(
        modifier = Modifier
          .padding(bottom = 8.dp)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = metric.display_name,
          style = MaterialTheme.typography.body1
        )
        Row {
          Text(
            text = stringResource(R.string.workout_details_graph_average_label)
              .format("${metric.average_value.formatDecimal()} ${metric.display_unit}"),
            style = MaterialTheme.typography.caption
          )
          Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.workout_details_graph_max_label)
              .format("${metric.max_value.formatDecimal()} ${metric.display_unit}"),
            style = MaterialTheme.typography.caption
          )
        }
      }
      LineChart(
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp)
          .padding(horizontal = 12.dp, vertical = 12.dp),
        lineChartData = metric.toChart(secondsSincePedalingStart),
        animation = tween(durationMillis = 1000, delayMillis = 250, easing = LinearEasing),
        lineDrawer = SolidLineDrawer(
          thickness = 1.dp,
          color = MaterialTheme.colors.primary
        ),
        pointDrawer = NoPointDrawer,
        xAxisDrawer = SimpleXAxisDrawer(
          axisLineColor = Color.White,
          labelRatio = secondsSincePedalingStart.size / 10, // We mainly just want around 10 labels.
          labelTextSize = 9.sp,
          labelTextColor = Color.White,
          axisLineThickness = 1.dp
        ),
        yAxisDrawer = SimpleYAxisDrawer(
          axisLineColor = Color.White,
          labelTextColor = Color.White,
          labelTextSize = 9.sp,
          axisLineThickness = 1.dp,
          labelValueFormatter = { value ->
            "${value.roundToInt()} ${metric.display_unit}"
          }
        )
      )
    }
  }
}

private fun Metric.toChart(
  seconds_since_pedaling_start: List<Int>
): LineChartData {
  val points = values
    .mapIndexed { index, value ->
      Log.e("TARAS", "seconds $seconds_since_pedaling_start")
      val label = seconds_since_pedaling_start[index].toMinutes()

      Point(value = value.toFloat(), label = label)
    }

  return LineChartData(
    points = points,
    startAtZero = false
  )
}

// This int is the seconds.
private fun Int.toMinutes(): String {
  // We want to output "12:50".
  val minutes = this / 60
  val seconds = this - (minutes * 60)

  return if (minutes == 0) {
    "00:${seconds.toNearestZero()}"
  } else {
    "$minutes:${seconds.toNearestZero()}"
  }
}

private fun Int.toNearestZero(): String {
  val modulus = this % 5
  val formattedInt = if (modulus >= 3) {
    this + (5 - modulus)
  } else {
    this - modulus
  }

  return when (formattedInt) {
    0 -> "00"
    5 -> "05"
    else -> formattedInt.toString()
  }
}
