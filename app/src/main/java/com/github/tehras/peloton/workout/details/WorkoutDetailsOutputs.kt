package com.github.tehras.peloton.workout.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.Metric
import com.github.tehras.data.data.Summary
import com.github.tehras.peloton.R
import com.github.tehras.peloton.common.Grid
import com.github.tehras.peloton.utils.format

@Composable
fun WorkoutDetailsOutputs(workout: WorkoutData) {
    TotalOutputs(workout.workoutPerformance.summaries)
    AverageOutputs(workout.workoutPerformance.metrics)
}

@Composable
private fun AverageOutputs(metrics: List<Metric>) {
    if (metrics.isEmpty()) return

    Card(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.workout_details_average_label)
            )
            Grid(
                rowSize = 3,
                data = metrics
            ) { metric ->
                MetricItem(metric = metric)
            }
        }
    }
}

@Composable
private fun TotalOutputs(summaries: List<Summary>) {
    if (summaries.isEmpty()) return

    Card(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.workout_details_total_label)
            )
            Grid(
                rowSize = 3,
                data = summaries
            ) { summary ->
                SummaryItem(summary = summary)
            }
        }
    }
}

@Composable
private fun SummaryItem(summary: Summary) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValueLabel(summary.value.format(), summary.display_unit)
        Text(
            text = summary.display_name,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
private fun MetricItem(metric: Metric) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValueLabel(metric.average_value.format(), metric.display_unit)
        Text(
            text = metric.display_name,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
private fun ValueLabel(value: String, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = value,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
