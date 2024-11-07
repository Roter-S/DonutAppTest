package com.example.donutapptest.ui.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.R
import com.example.donutapptest.data.repository.FakeUserRepository
import com.example.donutapptest.ui.components.OutlinedRoundedField
import com.example.donutapptest.ui.components.ScaffoldNotification

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val viewModel by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedRoundedField(
                value = viewModel.username,
                onValueChange = { loginViewModel.onUsernameChange(it, context) },
                label = stringResource(id = R.string.login_username_label),
                placeholder = stringResource(id = R.string.login_username_placeholder),
                keyboardType = KeyboardType.Text,
                enabled = !viewModel.isLoading,
                errorMessage = viewModel.usernameError,
            )

            OutlinedRoundedField(
                value = viewModel.password,
                onValueChange = { loginViewModel.onPasswordChange(it, context) },
                label = stringResource(id = R.string.login_password_label),
                placeholder = stringResource(id = R.string.login_password_placeholder),
                keyboardType = KeyboardType.Password,
                enabled = !viewModel.isLoading,
                errorMessage = viewModel.passwordError,
            )

            Button(
                onClick = {
                    loginViewModel.validateLogin(onResult = { isLoginSuccessful ->
                        if (isLoginSuccessful) {
                            navController.navigate("home")
                        }
                    }, context = context)
                },
                enabled = !viewModel.isLoading && viewModel.isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                if (viewModel.isLoading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(20.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.common_loading),
                            fontSize = 16.sp,
                        )
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.login_button),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login_register_prompt),
                )
                Text(
                    text = stringResource(id = R.string.register_here),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            navController.navigate("register")
                        }
                )
            }

        }
        viewModel.errorMessage?.let {
            ScaffoldNotification(
                scope = scope,
                message = it,
                onDismiss = loginViewModel::clearErrorMessage,
                type = "error"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    val fakeRepository = FakeUserRepository()
    val loginViewModel = LoginViewModel(fakeRepository)
    LoginScreen(
        navController = navController,
        loginViewModel = loginViewModel,
    )
}