package com.example.donutapptest.ui.views.login.model

sealed interface LoginUiEvent {
    data object NavigateToHome : LoginUiEvent
}
