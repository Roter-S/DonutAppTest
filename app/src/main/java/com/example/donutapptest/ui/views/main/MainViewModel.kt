package com.example.donutapptest.ui.views.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.domain.usecase.auth.CheckSessionUseCase
import com.example.donutapptest.domain.usecase.auth.GetOnboardingStatusUseCase
import com.example.donutapptest.domain.usecase.auth.LogoutUseCase
import com.example.donutapptest.domain.usecase.auth.SetOnboardingCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
) : ViewModel() {

    val isUserLoggedIn: StateFlow<Boolean?> = checkSessionUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val username: StateFlow<String?> = checkSessionUseCase.getActiveUsername()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val isOnboardingCompleted: StateFlow<Boolean?> = getOnboardingStatusUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean> = _isLoggedOut.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _isLoggedOut.value = true
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            setOnboardingCompletedUseCase(true)
        }
    }

    fun resetLogoutState() {
        _isLoggedOut.value = false
    }
}
