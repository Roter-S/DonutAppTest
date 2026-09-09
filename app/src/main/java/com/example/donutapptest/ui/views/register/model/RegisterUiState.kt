package com.example.donutapptest.ui.views.register.model

import com.example.donutapptest.core.common.UiText

data class RegisterUiState(
    val firstName: String = "",
    val firstNameTouched: Boolean = false,
    val firstNameError: UiText? = null,

    val lastName: String = "",
    val lastNameTouched: Boolean = false,
    val lastNameError: UiText? = null,

    val email: String = "",
    val emailTouched: Boolean = false,
    val emailError: UiText? = null,

    val password: String = "",
    val passwordTouched: Boolean = false,
    val passwordError: UiText? = null,

    val confirmPassword: String = "",
    val confirmPasswordTouched: Boolean = false,
    val confirmPasswordError: UiText? = null,

    val isFormValid: Boolean = false,
    val isLoading: Boolean = false
)
