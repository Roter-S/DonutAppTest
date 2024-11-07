package com.example.donutapptest.ui.views.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.R
import com.example.donutapptest.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val username: String = "",
    val usernameError: String? = null,
    val usernameTouched: Boolean = false,
    val password: String = "",
    val passwordError: String? = null,
    val passwordTouched: Boolean = false,
    val errorMessage: String? = null,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUsernameChange(newUsername: String, context: Context) {
        _uiState.value = _uiState.value.copy(username = newUsername, usernameTouched = true)
        validateForm(context)
    }

    fun onPasswordChange(newPassword: String, context: Context) {
        _uiState.value = _uiState.value.copy(password = newPassword, passwordTouched = true)
        validateForm(context)
    }

    private fun validateForm(context: Context) {
        val usernameValid = _uiState.value.username.length >= 8
        val passwordValid =
            _uiState.value.password.length >= 6 && _uiState.value.password.any { it.isUpperCase() }

        _uiState.value = _uiState.value.copy(
            isFormValid = usernameValid && passwordValid,
            usernameError = if (!usernameValid && _uiState.value.usernameTouched) context.getString(
                R.string.error_message_auth_username_length_error
            ) else null,
            passwordError = if (!passwordValid && _uiState.value.passwordTouched) context.getString(
                R.string.error_message_auth_password_complexity_error
            ) else null
        )
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun validateLogin(onResult: (Boolean) -> Unit, context: Context) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(1000)
            val isUserValid =
                userRepository.checkUserPassword(_uiState.value.username, _uiState.value.password)
            if (isUserValid) {
                _uiState.value = _uiState.value.copy(isLoading = false, isLoginSuccessful = true)
                onResult(true)
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccessful = false,
                    errorMessage = context.getString(
                        R.string.error_message_auth_invalid_credentials
                    )
                )
                onResult(false)
            }
        }
    }
}