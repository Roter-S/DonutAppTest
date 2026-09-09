package com.example.donutapptest.ui.views.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.core.common.Result
import com.example.donutapptest.domain.usecase.auth.LoginUseCase
import com.example.donutapptest.domain.usecase.auth.ValidatePasswordUseCase
import com.example.donutapptest.domain.usecase.auth.ValidateUsernameUseCase
import com.example.donutapptest.ui.common.AppNotificationManager
import com.example.donutapptest.ui.views.login.model.LoginUiEvent
import com.example.donutapptest.ui.views.login.model.LoginUiState
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val notificationManager: AppNotificationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onUsernameChange(newUsername: String) {
        _uiState.update {
            it.copy(
                username = newUsername,
                usernameTouched = true
            )
        }
        validateForm()
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update {
            it.copy(
                password = newPassword,
                passwordTouched = true
            )
        }
        validateForm()
    }

    private fun validateForm() {
        val currentState = _uiState.value
        val usernameValidation = validateUsernameUseCase(currentState.username)
        val passwordValidation = validatePasswordUseCase(currentState.password, requireComplexity = true)

        val isFormValid = usernameValidation.successful && passwordValidation.successful

        _uiState.update {
            it.copy(
                isFormValid = isFormValid,
                usernameError = if (!usernameValidation.successful && it.usernameTouched && it.username.isNotEmpty()) {
                    usernameValidation.errorMessage
                } else null,
                passwordError = if (!passwordValidation.successful && it.passwordTouched && it.password.isNotEmpty()) {
                    passwordValidation.errorMessage
                } else null
            )
        }
    }

    fun login() {
        if (!_uiState.value.isFormValid || _uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = loginUseCase(
                emailOrUsername = _uiState.value.username,
                password = _uiState.value.password
            )

            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEvent.send(LoginUiEvent.NavigateToHome)
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    result.message?.let { msg ->
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