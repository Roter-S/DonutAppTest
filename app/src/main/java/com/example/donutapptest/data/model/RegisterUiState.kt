package com.example.donutapptest.data.model

data class RegisterUiState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val firstNameTouched: Boolean = false,
    val lastName: String = "",
    val lastNameError: String? = null,
    val lastNameTouched: Boolean = false,
    val email: String = "",
    val emailError: String? = null,
    val emailTouched: Boolean = false,
    val password: String = "",
    val passwordError: String? = null,
    val passwordTouched: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val confirmPasswordTouched: Boolean = false,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val isRegisterSuccessful: Boolean = false
)