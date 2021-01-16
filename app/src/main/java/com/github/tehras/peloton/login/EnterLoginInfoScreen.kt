package com.github.tehras.peloton.login

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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

    ScrollableColumn(
        modifier = Modifier.fillMaxSize(),
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
            onImeActionPerformed = { action, _ ->
                if (action == ImeAction.Next) {
                    focusRequester.requestFocus()
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            onValueChange = { usernameField = it }
        )
        OutlinedTextField(
            modifier = focusRequesterModifier,
            label = { Text(text = stringResource(id = R.string.password_label)) },
            value = passwordField,
            onImeActionPerformed = { action, softwareController ->
                if (action == ImeAction.Done) {
                    softwareController?.hideSoftwareKeyboard()
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            onValueChange = { passwordField = it }
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
