package com.example.donutapptest.ui.views.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    val username: StateFlow<String?> =
        sessionManager.username.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            null
        )

    val isLoggedIn: StateFlow<Boolean?> =
        sessionManager.isLoggedIn.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            sessionManager.clearSession()
            onLoggedOut()
        }
    }
} 