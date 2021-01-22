package com.github.tehras.peloton.user

import androidx.compose.animation.asDisposableClock
import androidx.compose.animation.core.AnimationClockObservable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.AnimationEndReason.TargetReached
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.platform.AmbientAnimationClock
import com.github.tehras.peloton.user.SlideUpSheetState.SlideUpSheetValue
import com.github.tehras.peloton.user.SlideUpSheetState.SlideUpSheetValue.Collapsed
import com.github.tehras.peloton.user.SlideUpSheetState.SlideUpSheetValue.Expanded

@Composable
@ExperimentalMaterialApi
fun rememberSlideUpSheetState(
  initialValue: SlideUpSheetValue = Collapsed,
  animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
  confirmStateChange: (SlideUpSheetValue) -> Boolean = { true }
): SlideUpSheetState {
  val disposableClock = AmbientAnimationClock.current.asDisposableClock()
  return rememberSavedInstanceState(
    disposableClock,
    saver = SlideUpSheetState.Saver(
      clock = disposableClock,
      animationSpec = animationSpec,
      confirmStateChange = confirmStateChange
    )
  ) {
    SlideUpSheetState(
      initialValue = initialValue,
      clock = disposableClock,
      animationSpec = animationSpec,
      confirmStateChange = confirmStateChange
    )
  }
}

@ExperimentalMaterialApi
class SlideUpSheetState(
  initialValue: SlideUpSheetValue,
  clock: AnimationClockObservable,
  animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
  confirmStateChange: (SlideUpSheetValue) -> Boolean = { true }
) : SwipeableState<SlideUpSheetValue>(
  initialValue = initialValue,
  clock = clock,
  animationSpec = animationSpec,
  confirmStateChange = confirmStateChange
) {
  /** Whether the slideUpSheet is expanded. **/
  val isExpanded: Boolean
    get() = value == Expanded

  /** Whether the bottom sheet is collapsed. **/
  val isCollapsed: Boolean
    get() = value == Collapsed

  /**
   * Expand the slide up sheet, with an animation.
   */
  fun expand(onExpanded: () -> Unit = {}) {
    animateTo(
      Expanded,
      onEnd = { endReason, _ ->
        if (endReason == AnimationEndReason.TargetReached) {
          onExpanded()
        }
      }
    )
  }

  /**
   * Collapse the slide up sheet, with an animation.
   */
  fun collapse(onCollapsed: () -> Unit = {}) {
    animateTo(
      Collapsed,
      onEnd = { endReason, _ ->
        if (endReason == TargetReached) {
          onCollapsed()
        }

      }
    )
  }

  companion object {
    fun Saver(
      clock: AnimationClockObservable,
      animationSpec: AnimationSpec<Float>,
      confirmStateChange: (SlideUpSheetValue) -> Boolean
    ): Saver<SlideUpSheetState, *> = Saver(
      save = { it.value },
      restore = {
        SlideUpSheetState(
          initialValue = it,
          clock = clock,
          animationSpec = animationSpec,
          confirmStateChange = confirmStateChange
        )
      }
    )
  }

  enum class SlideUpSheetValue {
    Collapsed,
    Expanded
  }
}