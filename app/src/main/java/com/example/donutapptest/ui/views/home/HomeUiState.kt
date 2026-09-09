package com.example.donutapptest.ui.views.home

import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.domain.model.Donut

data class HomeUiState(
    val donuts: List<Donut> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)
