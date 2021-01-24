package com.github.tehras.peloton.workout.details

import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.LineChartData.Point
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.NoPointDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.PieChartData.Slice
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.github.tehras.data.data.Metric
import com.github.tehras.data.data.Zone
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
private fun WorkoutDetailsGraph(metric: Metric, secondsSincePedalingStart: List<Int>) {
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
        animation = tween(durationMillis = 750, delayMillis = 100, easing = FastOutLinearInEasing),
        lineDrawer = SolidLineDrawer(
          thickness = 1.dp,
          color = MaterialTheme.colors.primary
        ),
        pointDrawer = NoPointDrawer,
        xAxisDrawer = SimpleXAxisDrawer(
          axisLineColor = MaterialTheme.colors.onBackground,
          labelRatio = secondsSincePedalingStart.size / 10, // We mainly just want around 10 labels.
          labelTextSize = 9.sp,
          labelTextColor = MaterialTheme.colors.onBackground,
          axisLineThickness = 1.dp
        ),
        yAxisDrawer = SimpleYAxisDrawer(
          axisLineColor = MaterialTheme.colors.onBackground,
          labelTextColor = MaterialTheme.colors.onBackground,
          labelTextSize = 9.sp,
          axisLineThickness = 1.dp,
          labelValueFormatter = { value ->
            "${value.roundToInt()} ${metric.display_unit}"
          }
        )
      )
      metric.zones?.let { zones ->
        WorkoutZones(
          modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
          zones = zones
        )
      }
    }
  }
}

@Composable
private fun WorkoutZones(
  modifier: Modifier,
  zones: List<Zone>
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    PieChart(
      pieChartData = zones.toData(),
      modifier = modifier.size(80.dp),
      sliceDrawer = SimpleSliceDrawer(
        sliceThickness = 30f
      )
    )
    Column(modifier = Modifier.padding(start = 12.dp)) {
      val totalDuration = zones.sumBy { it.duration }

      zones.forEachIndexed { index, zone ->
        if (zone.duration > 0) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Canvas(Modifier.size(8.dp)) {
              drawIntoCanvas {
                drawRoundRect(
                  color = ZoneColors.zoneColors[index],
                  size = Size(width = 8.dp.toPx(), height = 8.dp.toPx()),
                  cornerRadius = CornerRadius(2.dp.toPx())
                )
              }
            }
            Text(
              modifier = Modifier.padding(start = 8.dp),
              text = "${zone.display_name} - ${zone.range}",
              style = MaterialTheme.typography.caption
            )
            Text(
              modifier = Modifier.padding(start = 4.dp),
              text = "(${((zone.duration * 100.0) / totalDuration).formatDecimal()}%)",
              style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }
  }
}

private fun List<Zone>.toData(): PieChartData {
  val slices = this.mapIndexed { index, zone ->
    Slice(
      value = zone.duration.toFloat(),
      color = ZoneColors.zoneColors[index]
    )
  }

  return PieChartData(slices = slices)
}

private fun Metric.toChart(
  seconds_since_pedaling_start: List<Int>
): LineChartData {
  val points = values
    .mapIndexed { index, value ->
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
