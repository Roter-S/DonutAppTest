package com.example.donutapptest.domain.usecase.donuts

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.domain.repository.DonutRepository
import javax.inject.Inject

class GetDonutsUseCase @Inject constructor(
    private val donutRepository: DonutRepository
) {
    suspend operator fun invoke(): Result<List<Donut>> {
        return donutRepository.getDonuts()
    }
}
