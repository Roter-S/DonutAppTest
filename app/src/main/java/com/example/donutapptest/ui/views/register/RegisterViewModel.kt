package com.example.donutapptest.ui.views.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.R
import com.example.donutapptest.core.common.Result
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.domain.usecase.auth.RegisterUseCase
import com.example.donutapptest.domain.usecase.auth.ValidateEmailUseCase
import com.example.donutapptest.domain.usecase.auth.ValidatePasswordUseCase
import com.example.donutapptest.ui.common.AppNotificationManager
import com.example.donutapptest.ui.views.register.model.RegisterUiEvent
import com.example.donutapptest.ui.views.register.model.RegisterUiState
import com.example.donutapptest.utils.enums.Alerts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val notificationManager: AppNotificationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<RegisterUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onFirstNameChange(newValue: String) {
        _uiState.update {
            it.copy(
                firstName = newValue,
                firstNameTouched = true,
                firstNameError = null
            )
        }
        validateForm()
    }

    fun onLastNameChange(newValue: String) {
        _uiState.update {
            it.copy(
                lastName = newValue,
                lastNameTouched = true,
                lastNameError = null
            )
        }
        validateForm()
    }

    fun onEmailChange(newValue: String) {
        _uiState.update {
            it.copy(
                email = newValue,
                emailTouched = true,
                emailError = null
            )
        }
        validateForm()
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(
                password = newValue,
                passwordTouched = true,
                passwordError = null
            )
        }
        validateForm()
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(
                confirmPassword = newValue,
                confirmPasswordTouched = true,
                confirmPasswordError = null
            )
        }
        validateForm()
    }

    private fun validateForm() {
        val state = _uiState.value
        var isValid = true

        val firstNameError = if (state.firstNameTouched && state.firstName.isBlank()) {
            isValid = false
            UiText.StringResource(R.string.error_firstname_required)
        } else null

        val lastNameError = if (state.lastNameTouched && state.lastName.isBlank()) {
            isValid = false
            UiText.StringResource(R.string.error_lastname_required)
        } else null

        val emailValidation = validateEmailUseCase(state.email)
        val emailError = if (state.emailTouched && !emailValidation.successful) {
            isValid = false
            emailValidation.errorMessage
        } else null

        val passwordValidation = validatePasswordUseCase(state.password)
        val passwordError = if (state.passwordTouched && !passwordValidation.successful) {
            isValid = false
            passwordValidation.errorMessage
        } else null

        val confirmPasswordError = if (state.confirmPasswordTouched && state.password != state.confirmPassword) {
            isValid = false
            UiText.StringResource(R.string.error_passwords_not_matching)
        } else null

        val allFilled = state.firstName.isNotBlank() &&
                state.lastName.isNotBlank() &&
                state.email.isNotBlank() &&
                state.password.isNotBlank() &&
                state.confirmPassword.isNotBlank()

        _uiState.update {
            it.copy(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                isFormValid = isValid && allFilled
            )
        }
    }

    fun register() {
        if (!_uiState.value.isFormValid || _uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentState = _uiState.value
            val result = registerUseCase(
                firstName = currentState.firstName,
                lastName = currentState.lastName,
                email = currentState.email,
                password = currentState.password
            )

            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEvent.send(RegisterUiEvent.NavigateToHome)
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    result.message?.let { msg ->
                        _uiState.update { it.copy(emailError = msg) }
                        notificationManager.showNotification(msg, Alerts.ERROR)
                    }
                }
                is Result.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}
