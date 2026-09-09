package com.example.donutapptest.ui.views.register.model

sealed interface RegisterUiEvent {
    data object NavigateToHome : RegisterUiEvent
}
