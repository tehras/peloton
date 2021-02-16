package com.github.tehras.peloton.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> Grid(
  data: List<T>,
  rowSize: Int = 1,
  itemContent: @Composable BoxScope.(T) -> Unit
) {
  val rows = data.windowed(rowSize, rowSize, true)

  rows.forEach { row ->
    Row(modifier = Modifier.fillMaxWidth()) {
      val weight = 1f / rowSize
      row.forEach { item ->
        Box(modifier = Modifier.weight(weight = weight)) {
          itemContent(item)
        }
      }
      if (row.size < rowSize) {
        for (i in 0 until (rowSize - row.size)) {
          Box(modifier = Modifier.weight(weight = weight))
        }
      }
    }
  }
}