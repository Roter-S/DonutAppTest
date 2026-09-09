package com.example.donutapptest.ui.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.core.common.Result
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.domain.usecase.donuts.GetDonutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDonutsUseCase: GetDonutsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDonuts()
    }

    fun loadDonuts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = getDonutsUseCase()) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            donuts = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
                is Result.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun toggleFavorite(donut: Donut, isFavorite: Boolean) {
        _uiState.update { state ->
            val updatedDonuts = state.donuts.map {
                if (it.id == donut.id) it.copy(isFavorite = isFavorite) else it
            }
            state.copy(donuts = updatedDonuts)
        }
    }
}
