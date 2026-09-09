package com.example.donutapptest.domain.usecase.auth

import com.example.donutapptest.R
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.domain.model.ValidationResult
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    private val emailPattern = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    )

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_invalid_email)
            )
        }
        if (!emailPattern.matcher(email.trim()).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.error_invalid_email)
            )
        }
        return ValidationResult(successful = true)
    }
}
