package com.github.tehras.peloton.login

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.ExperimentalFocus
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.home.Home
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.inject

@ExperimentalMaterialApi
@ExperimentalFocus
@ExperimentalCoroutinesApi
@Composable
fun LoginScreen(navigateToHome: () -> Unit) {
    val loginViewModel: LoginViewModel by inject()

    EnterLoginInfoScreen(
        loginViewModel = loginViewModel,
        navigateToHome = navigateToHome
    )
}

@Parcelize
object Login : Screen {
    @ExperimentalMaterialApi
    @ExperimentalFocus
    @FlowPreview
    @ExperimentalCoroutinesApi
    @Composable
    override fun compose(navigateTo: (Screen) -> Unit) {
        LoginScreen { navigateTo(Home) }
    }
}