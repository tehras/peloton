package com.github.tehras.peloton.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.github.tehras.peloton.common.CoilImage

@Composable
fun Avatar(
  url: String,
  contentDescription: String,
  modifier: Modifier
) {
  CoilImage(
    data = url,
    contentDescription = contentDescription,
    modifier = modifier
      .size(64.dp)
      .border(
        border = BorderStroke(4.dp, color = MaterialTheme.colors.background),
        shape = CircleShape
      )
      .shadow(
        elevation = 2.dp,
        shape = CircleShape,
        clip = false
      )
      .padding(2.dp)
  ) {
    transformations(CircleCropTransformation())
  }
}