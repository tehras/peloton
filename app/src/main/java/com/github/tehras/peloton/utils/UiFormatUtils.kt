package com.github.tehras.peloton.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

private val dateFormatter = SimpleDateFormat("E M/d/yy '@' h:mm a", Locale.US)

fun Long.toDate(): String = dateFormatter.format(Date(this * 1000))
fun Double.formatWork(): String = (this / 1000.0).roundToInt().toString()
fun Double.formatDecimal(): String = NumberFormat.getNumberInstance().apply {
  maximumFractionDigits = 2
}.format(this)

fun Int.formatDecimal(): String = NumberFormat.getNumberInstance().format(this)
fun Long.formatDecimal(): String = NumberFormat.getNumberInstance().format(this)
fun Double.format(): String = NumberFormat.getNumberInstance().format(this)