package com.example.donutapptest.ui.views.login.model

import com.example.donutapptest.core.common.UiText

data class LoginUiState(
    val username: String = "",
    val usernameError: UiText? = null,
    val usernameTouched: Boolean = false,
    val password: String = "",
    val passwordError: UiText? = null,
    val passwordTouched: Boolean = false,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false
)
