package com.example.donutapptest.ui.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.R
import com.example.donutapptest.ui.components.OutlinedRoundedField
import com.example.donutapptest.ui.components.ScaffoldNotification
import com.example.donutapptest.ui.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                OutlinedRoundedField(
                    value = uiState.username,
                    onValueChange = { loginViewModel.onUsernameChange(it) },
                    label = stringResource(id = R.string.login_username_label),
                    placeholder = stringResource(id = R.string.login_username_placeholder),
                    keyboardType = KeyboardType.Text
                )

                OutlinedRoundedField(
                    value = uiState.password,
                    onValueChange = { loginViewModel.onPasswordChange(it) },
                    label = stringResource(id = R.string.login_password_label),
                    placeholder = stringResource(id = R.string.login_password_placeholder),
                    keyboardType = KeyboardType.Password,
                )

                Button(
                    onClick = {
                        loginViewModel.validateLogin(context) {
                            if (it) {
                                navController.navigate("home")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.login_button),
                        modifier = Modifier.padding(10.dp)
                    )
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
        }
        uiState.errorMessage?.let {
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
    val loginViewModel = remember {
        LoginViewModel()
    }
    LoginScreen(navController = navController, loginViewModel = loginViewModel)
}