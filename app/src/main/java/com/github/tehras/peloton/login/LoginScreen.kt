package com.github.tehras.peloton.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.focus.ExperimentalFocus
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.home.Home
import com.github.tehras.peloton.shared.LoadingScreen
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.inject

@ExperimentalFocus
@ExperimentalCoroutinesApi
@Composable
fun LoginScreen(navigateToHome: () -> Unit) {
    val loginViewModel: LoginViewModel by inject()

    val state: State<LoginState> = loginViewModel.loginState.collectAsState()

    when (state.value) {
        LoginState.EnteringInfo -> EnterLoginInfoScreen(loginViewModel)
        LoginState.SubmittingInfo -> LoadingScreen()
        LoginState.FinishedSuccessfully -> navigateToHome()
    }
}

@Parcelize
object Login : Screen {
    @ExperimentalFocus
    @FlowPreview
    @ExperimentalCoroutinesApi
    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        LoginScreen { navigateTo(Home) }
    }
}