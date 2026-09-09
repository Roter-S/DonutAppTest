package com.example.donutapptest.domain.model

import com.example.donutapptest.core.common.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)
