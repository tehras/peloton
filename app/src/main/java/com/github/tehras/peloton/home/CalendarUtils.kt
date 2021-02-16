package com.github.tehras.peloton.home

import com.github.tehras.data.data.Month
import java.util.Calendar

internal typealias Week = List<Day>
private typealias Day = Pair<String?, Boolean>

internal fun generateWeeks(start: Calendar, end: Calendar, activeDays: List<Int>): List<Week> {
  val weeks = mutableListOf<Week>()
  var startOfWeek = start

  while (startOfWeek.before(end)) {
    val week = generateWeek(start = startOfWeek, end = end, activeDays = activeDays)
    // Now bump up the startOfWeek.
    startOfWeek = startOfWeek.apply {
      add(Calendar.DATE, week.filter { it.first != null }.size)
    }

    weeks += week

    if (week.last().first == null) {
      return weeks
    }
  }

  return weeks
}

internal fun pickMonthsToShow(months: List<Month>): List<Month> {
  val lastMonth = months.firstOrNull() ?: return emptyList()

  val current = Calendar.getInstance()
  if ((lastMonth.month - 1) == current.get(Calendar.MONTH) && current.get(Calendar.DAY_OF_WEEK) < 15) {
    val previousMonth = months.getOrNull(1)
    if (previousMonth != null) {
      return listOf(previousMonth, lastMonth)
    }
  }

  return listOf(lastMonth)
}

private fun generateWeek(start: Calendar, end: Calendar, activeDays: List<Int>): Week {
  val firstDay = start.get(Calendar.DAY_OF_WEEK)
  val lastDayOfMonth = end.get(Calendar.DAY_OF_MONTH)
  val firstDayOfMonth = start.get(Calendar.DAY_OF_MONTH)
  val today = Calendar.getInstance()
  val todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH)
  val isThisMonth = today.before(end) && today.after(start)

  val week = mutableListOf<Day>()

  for (i in Calendar.SUNDAY..Calendar.SATURDAY) {
    val dayOfMonth = (firstDayOfMonth + i - firstDay)
    val isAfterToday = isThisMonth && dayOfMonth > todayDayOfMonth

    val day = if (i >= firstDay && dayOfMonth <= lastDayOfMonth && !isAfterToday) {
      dayOfMonth.toString()
    } else {
      null
    }
    week += day to activeDays.contains(dayOfMonth)
  }

  return week
}