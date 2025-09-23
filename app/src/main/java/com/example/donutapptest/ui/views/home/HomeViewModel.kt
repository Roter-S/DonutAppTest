package com.example.donutapptest.ui.views.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.data.repository.DonutRepository
import com.example.donutapptest.domain.model.Donut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val donutRepository: DonutRepository
) : ViewModel() {
    var donuts = mutableStateListOf<Donut>()
        private set
    var isLoading by mutableStateOf(false)
        private set

    init {
        loadDonuts()
    }

    fun loadDonuts() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            val allDonuts = donutRepository.getDonuts()
            donuts.clear()
            donuts.addAll(allDonuts)
            isLoading = false
        }
    }
}
