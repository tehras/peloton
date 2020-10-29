package com.github.tehras.peloton.init

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import com.github.tehras.peloton.R
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.home.Home
import com.github.tehras.peloton.login.Login
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.inject

@ExperimentalCoroutinesApi
@Composable
fun InitScreen(navigateTo: (Screen) -> Unit) {
    val initViewModel: InitViewModel by inject()

    val state = initViewModel.onEvent.collectAsState()

    when (state.value) {
        InitState.Initializing -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(asset = vectorResource(id = R.drawable.ic_launcher_foreground))
        }
        InitState.UserDataNotFound -> navigateTo(Login)
        InitState.UserDataReady -> navigateTo(Home)
    }
}

@Parcelize
object Initialize : Screen {
    @FlowPreview
    @ExperimentalCoroutinesApi
    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        InitScreen(navigateTo)
    }
}