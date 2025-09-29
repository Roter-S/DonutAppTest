package com.example.donutapptest.ui.views.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.data.model.RegisterUiState
import com.example.donutapptest.data.repository.UserRepository
import com.example.donutapptest.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository, private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onFirstNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            firstName = newValue, firstNameTouched = true, firstNameError = null
        )
        validateForm()
    }

    fun onLastNameChange(newValue: String) {
        _uiState.value =
            _uiState.value.copy(lastName = newValue, lastNameTouched = true, lastNameError = null)
        validateForm()
    }

    fun onEmailChange(newValue: String, context: Context) {
        _uiState.value =
            _uiState.value.copy(email = newValue, emailTouched = true, emailError = null)
        validateForm()
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value =
            _uiState.value.copy(password = newValue, passwordTouched = true, passwordError = null)
        validateForm()
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = newValue, confirmPasswordTouched = true, confirmPasswordError = null
        )
        validateForm()
    }

    private fun validateForm() {
        val state = _uiState.value
        var isValid = true
        var firstNameError: String? = null
        var lastNameError: String? = null
        var emailError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null

        if (!state.firstNameTouched) firstNameError = null
        else if (state.firstName.isBlank()) {
            firstNameError = "El nombre es obligatorio"
            isValid = false
        }
        if (!state.lastNameTouched) lastNameError = null
        else if (state.lastName.isBlank()) {
            lastNameError = "El apellido es obligatorio"
            isValid = false
        }
        if (!state.emailTouched) emailError = null
        else if (!isValidEmail(state.email)) {
            emailError = "Correo electrónico inválido"
            isValid = false
        }
        if (!state.passwordTouched) passwordError = null
        else if (state.password.length < 6) {
            passwordError = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }
        if (!state.confirmPasswordTouched) confirmPasswordError = null
        else if (state.password != state.confirmPassword) {
            confirmPasswordError = "Las contraseñas no coinciden"
            isValid = false
        }
        _uiState.value = state.copy(
            firstNameError = firstNameError,
            lastNameError = lastNameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError,
            isFormValid = isValid
        )
    }

    fun validateRegister(onResult: (Boolean) -> Unit, context: Context) {
        val state = _uiState.value
        _uiState.value = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val userExists = userRepository.isUserRegistered(state.email)
            withContext(Dispatchers.Main) {
                if (userExists) {
                    _uiState.value = state.copy(
                        isLoading = false, emailError = "El usuario ya está registrado"
                    )
                    onResult(false)
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        userRepository.registerUser(
                            firstName = state.firstName,
                            lastName = state.lastName,
                            email = state.email,
                            password = state.password
                        )
                        sessionManager.setLoggedIn(true)
                        sessionManager.setUsername(state.email)
                        withContext(Dispatchers.Main) {
                            _uiState.value =
                                state.copy(isLoading = false, isRegisterSuccessful = true)
                            onResult(true)
                        }
                    }
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        )
        return emailPattern.matcher(email).matches()
    }
}
