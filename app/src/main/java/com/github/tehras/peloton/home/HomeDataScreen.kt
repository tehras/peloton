package com.github.tehras.peloton.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun HomeDataScreen(homeState: HomeState.Success) {
    Column {
        HeaderArea(data = homeState.userData)
    }
}