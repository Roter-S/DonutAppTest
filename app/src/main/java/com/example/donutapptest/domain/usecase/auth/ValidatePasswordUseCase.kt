package com.example.donutapptest.domain.usecase.auth

import com.example.donutapptest.R
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.domain.model.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String, requireComplexity: Boolean = false): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_password_length)
            )
        }
        if (requireComplexity && !password.any { it.isUpperCase() }) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_message_auth_password_complexity_error)
            )
        }
        return ValidationResult(successful = true)
    }
}
