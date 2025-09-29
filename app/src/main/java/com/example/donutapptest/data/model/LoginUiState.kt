package com.example.donutapptest.data.model

data class LoginUiState(
    val username: String = "",
    val usernameError: String? = null,
    val usernameTouched: Boolean = false,
    val password: String = "",
    val passwordError: String? = null,
    val passwordTouched: Boolean = false,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false
)
