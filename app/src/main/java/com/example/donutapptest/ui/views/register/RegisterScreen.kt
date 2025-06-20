package com.example.donutapptest.ui.views.register

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
fun RegisterScreen(
    navController: NavHostController, registerViewModel: RegisterViewModel
) {
    val uiState by registerViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    FormContainer(focusManager = focusManager) {
        LogoImage()
        OutlinedRoundedField(
            value = uiState.firstName,
            onValueChange = { registerViewModel.onFirstNameChange(it) },
            label = stringResource(id = R.string.register_firstname_label),
            placeholder = stringResource(id = R.string.register_firstname_placeholder),
            keyboardType = KeyboardType.Text,
            enabled = !uiState.isLoading,
            errorMessage = uiState.firstNameError,
        )
        OutlinedRoundedField(
            value = uiState.lastName,
            onValueChange = { registerViewModel.onLastNameChange(it) },
            label = stringResource(id = R.string.register_lastname_label),
            placeholder = stringResource(id = R.string.register_lastname_placeholder),
            keyboardType = KeyboardType.Text,
            enabled = !uiState.isLoading,
            errorMessage = uiState.lastNameError,
        )
        OutlinedRoundedField(
            value = uiState.email,
            onValueChange = { registerViewModel.onEmailChange(it, context) },
            label = stringResource(id = R.string.register_email_label),
            placeholder = stringResource(id = R.string.register_email_placeholder),
            keyboardType = KeyboardType.Email,
            enabled = !uiState.isLoading,
            errorMessage = uiState.emailError,
        )
        OutlinedRoundedField(
            value = uiState.password,
            onValueChange = { registerViewModel.onPasswordChange(it) },
            label = stringResource(id = R.string.register_password_label),
            placeholder = stringResource(id = R.string.register_password_placeholder),
            keyboardType = KeyboardType.Password,
            enabled = !uiState.isLoading,
            errorMessage = uiState.passwordError,
        )
        OutlinedRoundedField(
            value = uiState.confirmPassword,
            onValueChange = { registerViewModel.onConfirmPasswordChange(it) },
            label = stringResource(id = R.string.register_confirm_password_label),
            placeholder = stringResource(id = R.string.register_confirm_password_placeholder),
            keyboardType = KeyboardType.Password,
            enabled = !uiState.isLoading,
            errorMessage = uiState.confirmPasswordError,
        )
        LoadingButton(
            text = stringResource(id = R.string.register_button),
            isLoading = uiState.isLoading,
            isEnabled = uiState.isFormValid,
            onClick = {
                registerViewModel.validateRegister(onResult = { isRegisterSuccessful ->
                    if (isRegisterSuccessful) {
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
            promptTextId = R.string.register_login_prompt,
            actionTextId = R.string.register_login_here,
            navigationRoute = "login"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val fakeRepository = remember { FakeUserRepository() }
    val fakeSessionManager = remember { SessionManager(context) }
    val registerViewModel = remember { RegisterViewModel(fakeRepository, fakeSessionManager) }
    RegisterScreen(navController = navController, registerViewModel = registerViewModel)
}