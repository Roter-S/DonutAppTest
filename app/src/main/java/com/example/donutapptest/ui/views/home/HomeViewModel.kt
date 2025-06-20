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
    var page by mutableStateOf(1)
        private set
    val pageSize = 5
    var endReached by mutableStateOf(false)
        private set

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading || endReached) return
        isLoading = true
        viewModelScope.launch {
            val newDonuts = donutRepository.getDonuts(page, pageSize)
            if (newDonuts.isNotEmpty()) {
                donuts.addAll(newDonuts)
                page++
            } else {
                endReached = true
            }
            isLoading = false
        }
    }

    fun resetList() {
        donuts.clear()
        page = 1
        endReached = false
        loadNextPage()
    }
} 