package com.github.tehras.peloton.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun HomeDataScreen(homeData: HomeData.Success) {
    Column {
        HeaderArea(data = homeData.userData)
    }
}