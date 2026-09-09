package com.example.donutapptest.domain.usecase.donuts

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.domain.repository.DonutRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetDonutsUseCaseTest {

    @Test
    fun `when repository returns success then usecase returns success`() = runTest {
        val fakeDonuts = listOf(
            Donut(
                id = "1",
                type = "Glazed",
                name = "Classic Glazed",
                ppu = 1.25,
                imageUrl = "http://example.com/donut.png",
                batters = emptyList(),
                toppings = emptyList()
            )
        )

        val fakeRepository = object : DonutRepository {
            override suspend fun getDonuts(): Result<List<Donut>> {
                return Result.Success(fakeDonuts)
            }
        }

        val useCase = GetDonutsUseCase(fakeRepository)
        val result = useCase()

        assertTrue(result is Result.Success)
        assertEquals(fakeDonuts, (result as Result.Success).data)
    }

    @Test
    fun `when repository returns error then usecase propagates error`() = runTest {
        val fakeRepository = object : DonutRepository {
            override suspend fun getDonuts(): Result<List<Donut>> {
                return Result.Error(exception = RuntimeException("Network error"))
            }
        }

        val useCase = GetDonutsUseCase(fakeRepository)
        val result = useCase()

        assertTrue(result is Result.Error)
    }
}
