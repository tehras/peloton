package com.github.tehras.peloton

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import com.github.tehras.peloton.ui.PelotonTheme

@Composable
fun PelotonApp(navigationViewModel: NavigationViewModel) {
    PelotonTheme {
        AppContent(navigationViewModel = navigationViewModel)
    }
}

@Composable
fun AppContent(navigationViewModel: NavigationViewModel) {
    Crossfade(navigationViewModel.currentScreen) { screen ->
        screen.compose()
    }
}
