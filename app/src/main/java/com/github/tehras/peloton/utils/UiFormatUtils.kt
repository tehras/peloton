package com.github.tehras.peloton.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

private val dateFormatter = SimpleDateFormat("E M/d/yy '@' h:mm a", Locale.US)
private val decimalFormatter = DecimalFormat("#,###,###,###")

fun Long.toDate(): String = dateFormatter.format(Date(this * 1000))
fun Double.formatWork(): String = (this / 1000.0).roundToInt().toString()
fun Int.formatDecimal(): String = NumberFormat.getNumberInstance().format(this)
fun Long.formatDecimal(): String = NumberFormat.getNumberInstance().format(this)
fun Double.format(): String = NumberFormat.getNumberInstance().format(this)