package com.github.tehras.peloton

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.tehras.peloton.ui.PelotonTheme

@Composable
fun PelotonApp(navigationViewModel: NavigationViewModel) {
    PelotonTheme {
        AppContent(navigationViewModel = navigationViewModel)
    }
}

@Composable
fun AppContent(navigationViewModel: NavigationViewModel) {
    Crossfade(
        current = navigationViewModel.currentScreen,
        modifier = Modifier.fillMaxSize()
    ) { screen ->
        screen.compose { newScreen ->
            navigationViewModel.navigateTo(newScreen)
        }
    }
}
