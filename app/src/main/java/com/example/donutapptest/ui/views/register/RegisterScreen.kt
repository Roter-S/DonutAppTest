package com.example.donutapptest.ui.views.register

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
import com.example.donutapptest.ui.views.register.model.RegisterUiEvent
import com.example.donutapptest.ui.views.register.model.RegisterUiState

@Composable
fun RegisterRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is RegisterUiEvent.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    RegisterScreen(
        uiState = uiState,
        onFirstNameChange = viewModel::onFirstNameChange,
        onLastNameChange = viewModel::onLastNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onRegisterClick = viewModel::register,
        onLoginClick = onNavigateToLogin,
        modifier = modifier
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FormContainer(modifier = modifier) {
        LogoImage()

        OutlinedRoundedField(
            value = uiState.firstName,
            onValueChange = onFirstNameChange,
            label = stringResource(id = R.string.register_firstname_label),
            placeholder = stringResource(id = R.string.register_firstname_placeholder),
            keyboardType = KeyboardType.Text,
            enabled = !uiState.isLoading,
            errorMessage = uiState.firstNameError
        )

        OutlinedRoundedField(
            value = uiState.lastName,
            onValueChange = onLastNameChange,
            label = stringResource(id = R.string.register_lastname_label),
            placeholder = stringResource(id = R.string.register_lastname_placeholder),
            keyboardType = KeyboardType.Text,
            enabled = !uiState.isLoading,
            errorMessage = uiState.lastNameError
        )

        OutlinedRoundedField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = stringResource(id = R.string.register_email_label),
            placeholder = stringResource(id = R.string.register_email_placeholder),
            keyboardType = KeyboardType.Email,
            enabled = !uiState.isLoading,
            errorMessage = uiState.emailError
        )

        OutlinedRoundedField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.register_password_label),
            placeholder = stringResource(id = R.string.register_password_placeholder),
            keyboardType = KeyboardType.Password,
            enabled = !uiState.isLoading,
            errorMessage = uiState.passwordError
        )

        OutlinedRoundedField(
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(id = R.string.register_confirm_password_label),
            placeholder = stringResource(id = R.string.register_confirm_password_placeholder),
            keyboardType = KeyboardType.Password,
            enabled = !uiState.isLoading,
            errorMessage = uiState.confirmPasswordError
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoadingButton(
            text = stringResource(id = R.string.register_button),
            isLoading = uiState.isLoading,
            isEnabled = uiState.isFormValid,
            onClick = onRegisterClick
        )

        NavigationPromptRow(
            promptTextId = R.string.register_login_prompt,
            actionTextId = R.string.register_login_here,
            onActionClick = onLoginClick
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    DonutAppTestTheme {
        RegisterScreen(
            uiState = RegisterUiState(
                firstName = "John",
                lastName = "Doe",
                email = "john@example.com",
                isFormValid = true
            ),
            onFirstNameChange = {},
            onLastNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onRegisterClick = {},
            onLoginClick = {}
        )
    }
}