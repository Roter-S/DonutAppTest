package com.example.donutapptest.ui.views.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.donutapptest.R
import com.example.donutapptest.ui.components.FormContainer
import com.example.donutapptest.ui.components.LoadingButton
import com.example.donutapptest.ui.components.LogoImage
import com.example.donutapptest.ui.components.NavigationPromptRow
import com.example.donutapptest.ui.components.OutlinedRoundedField
import com.example.donutapptest.ui.theme.DonutAppTestTheme
import com.example.donutapptest.ui.views.login.model.LoginUiEvent
import com.example.donutapptest.ui.views.login.model.LoginUiState

@Composable
fun LoginRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onRegisterClick = onNavigateToRegister,
        modifier = modifier
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FormContainer(modifier = modifier) {
        LogoImage()

        OutlinedRoundedField(
            value = uiState.username,
            onValueChange = onUsernameChange,
            label = stringResource(id = R.string.login_username_label),
            placeholder = stringResource(id = R.string.login_username_placeholder),
            keyboardType = KeyboardType.Text,
            enabled = !uiState.isLoading,
            errorMessage = uiState.usernameError
        )

        OutlinedRoundedField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.login_password_label),
            placeholder = stringResource(id = R.string.login_password_placeholder),
            keyboardType = KeyboardType.Password,
            enabled = !uiState.isLoading,
            errorMessage = uiState.passwordError
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoadingButton(
            text = stringResource(id = R.string.login_button),
            isLoading = uiState.isLoading,
            isEnabled = uiState.isFormValid,
            onClick = onLoginClick
        )

        NavigationPromptRow(
            promptTextId = R.string.login_register_prompt,
            actionTextId = R.string.register_here,
            onActionClick = onRegisterClick
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    DonutAppTestTheme {
        LoginScreen(
            uiState = LoginUiState(
                username = "donutlover",
                isFormValid = true
            ),
            onUsernameChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}