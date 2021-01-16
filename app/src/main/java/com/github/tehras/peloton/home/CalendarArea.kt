package com.github.tehras.peloton.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.tehras.data.data.CalendarResponse
import com.github.tehras.data.data.Month
import com.github.tehras.peloton.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

@Composable
fun CalendarArea(
    calendarData: CalendarResponse
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(color = MaterialTheme.colors.background)
    ) {
        val monthsToShow = pickMonthsToShow(calendarData.months)

        if (monthsToShow.isNotEmpty()) {
            Column {
                CalendarTitle(monthsToShow)
                CalendarView(months = monthsToShow)
                CalendarViewFullLabel()
            }
        }
    }
}

@Composable
private fun CalendarViewFullLabel() {
    Divider()
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 12.dp)) {
        Text(
            text = stringResource(id = R.string.view_full_calendar_label),
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun CalendarTitle(monthsToShow: List<Month>) {
    val month = when {
        monthsToShow.isEmpty() -> return
        monthsToShow.size == 1 -> (monthsToShow.first().month - 1).toDisplay()
        else -> {
            val firstMonth = (monthsToShow.first().month - 1).toDisplay()
            val lastMonth = (monthsToShow.last().month - 1).toDisplay()

            "$firstMonth - $lastMonth"
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(
            text = month,
            style = MaterialTheme.typography.body2
        )
    }
    Divider()
}

private fun Int.toDisplay(): String {
    val c = getInstance().apply {
        set(MONTH, this@toDisplay)
    }

    return SimpleDateFormat("MMMM", Locale.US).format(c.timeInMillis)
}

@Composable
private fun CalendarView(months: List<Month>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        // Let's start by drawing the first row.
        CalendarLabelRow()
        months.forEach { month ->
            MonthView(month = month)
        }
    }
}

@Composable
private fun MonthView(month: Month) {
    // Start the day with Sunday.
    val start = getInstance().apply {
        set(MONTH, month.month - 1)
        set(YEAR, month.year)
        set(DAY_OF_MONTH, 1)
    }
    val end = getInstance().apply {
        set(MONTH, month.month)
        set(YEAR, month.year)
        set(DAY_OF_MONTH, 1)
        add(DATE, -1)
    }

    val weeks = generateWeeks(start = start, end = end, activeDays = month.active_days)

    Divider(modifier = Modifier.padding(vertical = 8.dp))
    weeks.forEach {
        Week(week = it)
    }
}

@Composable
private fun CalendarLabelRow() {
    Row(modifier = Modifier.fillMaxWidth()) {
        CalendarItem(text = "Sun")
        CalendarItem(text = "Mon")
        CalendarItem(text = "Tue")
        CalendarItem(text = "Wed")
        CalendarItem(text = "Thu")
        CalendarItem(text = "Fri")
        CalendarItem(text = "Sat")
    }
}

@Composable
private fun Week(week: Week) {
    Row(modifier = Modifier.fillMaxWidth()) {
        week.forEach { (label, isActive) ->
            CalendarItem(text = label, isActive = isActive)
        }
    }
}

@Composable
private fun RowScope.CalendarItem(text: String?, isActive: Boolean = false) {
    Column(
        modifier = Modifier.weight(1f, true)
            .apply {
                if (isActive) background(
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .size(36.dp)
                .background(
                    color = if (isActive) MaterialTheme.colors.secondary else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (text != null) {
                Text(
                    text = text.toString(),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}