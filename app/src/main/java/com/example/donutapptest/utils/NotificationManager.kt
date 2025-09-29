package com.example.donutapptest.utils

import com.example.donutapptest.data.model.NotificationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NotificationManager {
    private val _notificationState = MutableStateFlow<NotificationState?>(null)
    val notificationState: StateFlow<NotificationState?> = _notificationState

    fun showNotification(message: String, type: String = "SUCCESS") {
        _notificationState.value = NotificationState(message, type)
    }

    fun clearNotification() {
        _notificationState.value = null
    }
}