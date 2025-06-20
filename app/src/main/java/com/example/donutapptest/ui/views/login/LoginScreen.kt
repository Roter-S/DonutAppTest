package com.example.donutapptest.ui.views.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.R
import com.example.donutapptest.data.repository.FakeUserRepository
import com.example.donutapptest.data.session.SessionManager
import com.example.donutapptest.ui.components.FormContainer
import com.example.donutapptest.ui.components.LoadingButton
import com.example.donutapptest.ui.components.LogoImage
import com.example.donutapptest.ui.components.NavigationPromptRow
import com.example.donutapptest.ui.components.OutlinedRoundedField

@Composable
fun LoginScreen(
    navController: NavHostController, loginViewModel: LoginViewModel
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    FormContainer(focusManager = focusManager) {
        LogoImage()

        OutlinedRoundedField(
            value = uiState.username,
            onValueChange = { loginViewModel.onUsernameChange(it, context) },
            label = stringResource(id = R.string.login_username_label),
            placeholder = stringResource(id = R.string.login_username_placeholder),
            keyboardType = KeyboardType.Text,
            enabled = !uiState.isLoading,
            errorMessage = uiState.usernameError,
        )

        OutlinedRoundedField(
            value = uiState.password,
            onValueChange = { loginViewModel.onPasswordChange(it, context) },
            label = stringResource(id = R.string.login_password_label),
            placeholder = stringResource(id = R.string.login_password_placeholder),
            keyboardType = KeyboardType.Password,
            enabled = !uiState.isLoading,
            errorMessage = uiState.passwordError,
        )

        LoadingButton(
            text = stringResource(id = R.string.login_button),
            isLoading = uiState.isLoading,
            isEnabled = uiState.isFormValid,
            onClick = {
                loginViewModel.validateLogin(onResult = { isLoginSuccessful ->
                    if (isLoginSuccessful) {
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }, context = context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)

        )

        NavigationPromptRow(
            navController = navController,
            promptTextId = R.string.login_register_prompt,
            actionTextId = R.string.register_here,
            navigationRoute = "register"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val fakeRepository = remember { FakeUserRepository() }
    val fakeSessionManager = remember { SessionManager(context) }
    val loginViewModel = remember { LoginViewModel(fakeRepository, fakeSessionManager) }
    LoginScreen(
        navController = navController,
        loginViewModel = loginViewModel,
    )
}