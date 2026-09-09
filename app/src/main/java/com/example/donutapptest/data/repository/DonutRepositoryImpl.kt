package com.example.donutapptest.data.repository

import com.example.donutapptest.R
import com.example.donutapptest.core.common.Result
import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.data.mapper.toDomain
import com.example.donutapptest.data.remote.ApiService
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.domain.repository.DonutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DonutRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DonutRepository {

    override suspend fun getDonuts(): Result<List<Donut>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllDonuts()
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                Result.Success(body.map { it.toDomain() })
            } else {
                Result.Error(
                    exception = HttpException(response),
                    message = UiText.StringResource(R.string.error_network)
                )
            }
        } catch (e: IOException) {
            Result.Error(
                exception = e,
                message = UiText.StringResource(R.string.error_network)
            )
        } catch (e: Exception) {
            Result.Error(
                exception = e,
                message = UiText.StringResource(R.string.error_unknown)
            )
        }
    }
}
