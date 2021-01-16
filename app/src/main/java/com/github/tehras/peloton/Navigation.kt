package com.github.tehras.peloton

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.tehras.peloton.home.Home
import com.github.tehras.peloton.init.Initialize
import com.github.tehras.peloton.utils.getMutableStateOf

interface Screen : Parcelable {
    val isTopScreen: Boolean
        get() = true

    @Composable
    fun Compose(navigateTo: (Screen) -> Unit)
}

/**
 * Helpers for saving and loading a [Screen] object to a [Bundle].
 *
 * This allows us to persist navigation across process death, for example caused by a long video
 * call.
 */
private const val SIS_SCREEN = "sis_screen"
private const val SIS_NAME = "screen_name"
private const val SIS_POST = "post"

/**
 * Convert a screen to a bundle that can be stored in [SavedStateHandle]
 */
private fun Screen.toBundle(): Bundle {
    return bundleOf(SIS_NAME to this)
}

/**
 * Read a bundle stored by [Screen.toBundle] and return desired screen.
 *
 * @return the parsed [Screen]
 * @throws IllegalArgumentException if the bundle could not be parsed
 */
private fun Bundle.toScreen(): Screen {
    return requireNotNull(getParcelable(SIS_NAME)) { "Screen is missing." }
}

/**
 * This is expected to be replaced by the navigation component, but for now handle navigation
 * manually.
 *
 * Instantiate this ViewModel at the scope that is fully-responsible for navigation, which in this
 * application is [MainActivity].
 *
 * This app has simplified navigation; the back stack is always [Home] or [Home, dest] and more
 * levels are not allowed. To use a similar pattern with a longer back stack, use a [StateList] to
 * hold the back stack state.
 */
class NavigationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    // Convert stack?
    val backStack = mutableListOf<Screen>()

    /**
     * Hold the current screen in an observable, restored from savedStateHandle after process
     * death.
     *
     * mutableStateOf is an observable similar to LiveData that's designed to be read by compose. It
     * supports observability via property delegate syntax as shown here.
     */
    var currentScreen: Screen by savedStateHandle.getMutableStateOf<Screen>(
        key = SIS_SCREEN,
        default = Initialize,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set // limit the writes to only inside this class.

    /**
     * Go back (always to [Home]).
     *
     * Returns true if this call caused user-visible navigation. Will always return false
     * when [currentScreen] is [Home].
     */
    @MainThread
    fun onBack(): Boolean {
        val wasHandled = !currentScreen.isTopScreen
        if (wasHandled) {
            // Pop the current item.
            if (backStack.isNotEmpty()) backStack.removeLast()

            currentScreen = if (backStack.isNotEmpty()) {
                backStack.last()
            } else {
                Home
            }
        }
        return wasHandled
    }

    /**
     * Navigate to requested [Screen].
     *
     * If the requested screen is not [Home], it will always create a back stack with one element:
     * ([Home] -> [screen]). More back entries are not supported in this app.
     */
    @MainThread
    fun navigateTo(screen: Screen) {
        if (screen.isTopScreen) {
            backStack.clear()
        } else {
            backStack += screen
        }
        currentScreen = screen
    }
}