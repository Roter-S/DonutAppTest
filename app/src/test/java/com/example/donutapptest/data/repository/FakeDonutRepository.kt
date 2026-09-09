package com.example.donutapptest.data.repository

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.domain.repository.DonutRepository

class FakeDonutRepository : DonutRepository {

    var donutsList: List<Donut> = emptyList()
    var shouldReturnError: Boolean = false

    override suspend fun getDonuts(): Result<List<Donut>> {
        return if (shouldReturnError) {
            Result.Error(
                exception = RuntimeException("Network error"),
                message = UiText.DynamicString("Failed to load donuts")
            )
        } else {
            Result.Success(donutsList)
        }
    }
}
