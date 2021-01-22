package com.github.tehras.peloton.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.tehras.peloton.user.SlideUpSheetState.SlideUpSheetValue.Collapsed
import com.github.tehras.peloton.user.SlideUpSheetState.SlideUpSheetValue.Expanded

@ExperimentalMaterialApi
@Composable
fun SlideUpScreen(
  sheetContent: @Composable ColumnScope.() -> Unit,
  modifier: Modifier = Modifier,
  sheetState: SlideUpSheetState = rememberSlideUpSheetState(),
  sheetGesturesEnabled: Boolean,
  sheetShape: Shape = MaterialTheme.shapes.medium,
  sheetElevation: Dp = BottomSheetScaffoldDefaults.SheetElevation,
  sheetBackgroundColor: Color = MaterialTheme.colors.surface,
  sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
  bodyContent: @Composable () -> Unit
) {
  WithConstraints(modifier) {
    val fullHeight = constraints.maxHeight.toFloat()
    var swipeUpSheetHeight by remember { mutableStateOf(fullHeight) }

    val swipeable = Modifier
      .swipeable(
        state = sheetState,
        anchors = mapOf(
          fullHeight to Collapsed,
          fullHeight - swipeUpSheetHeight to Expanded
        ),
        orientation = Orientation.Vertical,
        enabled = sheetGesturesEnabled,
        resistance = null
      )

    val child = @Composable {
      SlideUpScaffoldStack(
        body = {
          Surface {
            Column(Modifier.fillMaxWidth()) {
              bodyContent()
            }
          }
        },
        slideUpSheet = {
          Surface(
            swipeable
              .fillMaxWidth()
              .heightIn(min = 0.dp)
              .onGloballyPositioned {
                swipeUpSheetHeight = it.size.height.toFloat()
              },
            shape = sheetShape,
            elevation = sheetElevation,
            color = sheetBackgroundColor,
            contentColor = sheetContentColor,
            content = { Column(content = sheetContent) }
          )
        }
        // swipeUpSheetOffset = swipeUpSheetHeight
      )
    }

    child()
  }
}

@Composable
private fun SlideUpScaffoldStack(
  body: @Composable () -> Unit,
  slideUpSheet: @Composable () -> Unit
  // swipeUpSheetOffset: State<Float>
) {
  Layout(
    content = {
      body()
      slideUpSheet()
    }
  ) { measurables, constraints ->
    val placeable = measurables.first().measure(constraints)

    layout(placeable.width, placeable.height) {
      placeable.placeRelative(0, 0)

      val (sheetPlaceable) = measurables.drop(1).map {
        it.measure(constraints.copy(minWidth = 0, minHeight = 0))
      }

      // val sheetOffsetY = swipeUpSheetOffset.value.roundToInt()
      val sheetOffsetY = 0

      sheetPlaceable.placeRelative(0, sheetOffsetY)
    }
  }
}
