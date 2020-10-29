package com.github.tehras.peloton.login

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.tehras.peloton.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFocus
@ExperimentalCoroutinesApi
@Composable
fun EnterLoginInfoScreen(loginViewModel: LoginViewModel) {
    var usernameField by remember { mutableStateOf(TextFieldValue()) }
    var passwordField by remember { mutableStateOf(TextFieldValue()) }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusRequesterModifier = Modifier.focusRequester(focusRequester)

        Text(
            text = stringResource(id = R.string.enter_your_information),
            style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.primary),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            label = { Text(text = stringResource(id = R.string.username_label)) },
            value = usernameField,
            imeAction = ImeAction.Next,
            onImeActionPerformed = { action, _ ->
                if (action == ImeAction.Next) {
                    focusRequester.requestFocus()
                }
            },
            onValueChange = { usernameField = it }
        )
        OutlinedTextField(
            modifier = focusRequesterModifier,
            label = { Text(text = stringResource(id = R.string.password_label)) },
            value = passwordField,
            imeAction = ImeAction.Done,
            onImeActionPerformed = { action, softwareController ->
                if (action == ImeAction.Done) {
                    softwareController?.hideSoftwareKeyboard()
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { passwordField = it }
        )
        TextButton(
            onClick = {
                loginViewModel.authenticate(
                    username = usernameField.text,
                    password = passwordField.text
                )
            }
        ) {
            Text(text = stringResource(id = R.string.login_button))
        }
    }
}