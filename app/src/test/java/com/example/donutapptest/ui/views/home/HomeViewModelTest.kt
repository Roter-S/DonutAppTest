package com.example.donutapptest.ui.views.home

import com.example.donutapptest.data.repository.FakeDonutRepository
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.domain.usecase.donuts.GetDonutsUseCase
import com.example.donutapptest.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeRepository = FakeDonutRepository()
    private val getDonutsUseCase = GetDonutsUseCase(fakeRepository)

    private val sampleDonuts = listOf(
        Donut(
            id = "1",
            type = "Glazed",
            name = "Glazed Donut",
            ppu = 1.0,
            imageUrl = "http://example.com/1.png",
            batters = emptyList(),
            toppings = emptyList(),
            isFavorite = false
        ),
        Donut(
            id = "2",
            type = "Chocolate",
            name = "Chocolate Donut",
            ppu = 1.5,
            imageUrl = "http://example.com/2.png",
            batters = emptyList(),
            toppings = emptyList(),
            isFavorite = false
        )
    )

    @Test
    fun `init loads donuts successfully and updates state`() = runTest {
        fakeRepository.donutsList = sampleDonuts

        val viewModel = HomeViewModel(getDonutsUseCase)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(2, state.donuts.size)
        assertEquals("Glazed Donut", state.donuts[0].name)
    }

    @Test
    fun `when loadDonuts fails then state contains error message`() = runTest {
        fakeRepository.shouldReturnError = true

        val viewModel = HomeViewModel(getDonutsUseCase)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.donuts.isEmpty())
        assertNotNull(state.errorMessage)
    }

    @Test
    fun `toggleFavorite toggles favorite status for the specific donut`() = runTest {
        fakeRepository.donutsList = sampleDonuts
        val viewModel = HomeViewModel(getDonutsUseCase)

        viewModel.toggleFavorite(sampleDonuts[0], isFavorite = true)

        val updatedState = viewModel.uiState.value
        assertTrue(updatedState.donuts[0].isFavorite)
        assertFalse(updatedState.donuts[1].isFavorite)
    }
}
