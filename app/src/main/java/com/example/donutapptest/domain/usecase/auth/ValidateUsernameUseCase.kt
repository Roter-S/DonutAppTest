package com.example.donutapptest.domain.usecase.auth

import com.example.donutapptest.R
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.domain.model.ValidationResult
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() {

    operator fun invoke(username: String): ValidationResult {
        if (username.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_message_auth_username_length_error)
            )
        }
        return ValidationResult(successful = true)
    }
}
