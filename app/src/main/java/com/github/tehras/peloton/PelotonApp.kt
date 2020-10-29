package com.github.tehras.peloton

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
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
        navigationViewModel.currentScreen,
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
    ) { screen ->
        screen.compose { newScreen ->
            navigationViewModel.navigateTo(newScreen)
        }
    }
}
