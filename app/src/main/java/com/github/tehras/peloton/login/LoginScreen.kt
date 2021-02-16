package com.github.tehras.peloton.login

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.github.tehras.peloton.Screen
import com.github.tehras.peloton.home.Home
import com.github.tehras.peloton.utils.getViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.parcelize.Parcelize

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun LoginScreen(navigateToHome: () -> Unit) {
  val loginViewModel: LoginViewModel = getViewModel()

  EnterLoginInfoScreen(
    loginViewModel = loginViewModel,
    navigateToHome = navigateToHome
  )
}

@Parcelize
object Login : Screen {
  @ExperimentalMaterialApi
  @FlowPreview
  @ExperimentalCoroutinesApi
  @Composable
  override fun Compose(navigateTo: (Screen) -> Unit) {
    LoginScreen { navigateTo(Home) }
  }
}