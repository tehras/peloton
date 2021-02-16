package com.github.tehras.peloton.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction.Done
import androidx.compose.ui.text.input.ImeAction.Next
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.tehras.peloton.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun EnterLoginInfoScreen(
  loginViewModel: LoginViewModel,
  navigateToHome: () -> Unit
) {
  val state = loginViewModel.loginState.collectAsState()

  var usernameField by remember { mutableStateOf("") }
  var passwordField by remember { mutableStateOf("") }
  val focusRequester = remember { FocusRequester() }

  val errorState = state.value as? LoginState.LoginError
  val enableSubmitButton = usernameField.isNotEmpty() && passwordField.isNotEmpty()
  val showLoading = state.value is LoginState.SubmittingInfo

  if (state.value is LoginState.FinishedSuccessfully) {
    navigateToHome()
  }

  Surface {
    Column(
      modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      val focusRequesterModifier = Modifier.focusRequester(focusRequester)

      Text(
        text = stringResource(id = R.string.enter_your_information),
        style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.secondary),
        textAlign = TextAlign.Center,
        modifier = Modifier
          .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
          .align(Alignment.CenterHorizontally)
      )
      OutlinedTextField(
        label = { Text(text = stringResource(id = R.string.username_label)) },
        value = usernameField,
        keyboardOptions = KeyboardOptions(imeAction = Next),
        onValueChange = { usernameField = it },
        keyboardActions = KeyboardActions(
          onNext = { focusRequester.requestFocus() }
        )
      )
      OutlinedTextField(
        modifier = focusRequesterModifier,
        value = passwordField,
        onValueChange = { /*TODO*/ },
        keyboardOptions = KeyboardOptions(imeAction = Done),
        visualTransformation = PasswordVisualTransformation(),
      )
      Column(modifier = Modifier.height(60.dp)) {
        Button(
          modifier = Modifier.padding(top = 24.dp),
          enabled = enableSubmitButton && !showLoading,
          onClick = {
            loginViewModel.authenticate(
              username = usernameField,
              password = passwordField
            )
          }
        ) {
          if (!showLoading) {
            Text(text = stringResource(id = R.string.login_button))
          } else {
            CircularProgressIndicator(
              modifier = Modifier.size(12.dp),
              strokeWidth = 2.dp,
              color = MaterialTheme.colors.background
            )
          }
        }
      }

      if (errorState != null) {
        Text(
          text = errorState.message,
          style = MaterialTheme.typography.caption.copy(color = Color.Red)
        )
      }
    }
  }
}
