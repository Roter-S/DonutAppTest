package com.example.donutapptest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donutapptest.data.Donut
import com.example.donutapptest.data.DonutApi
import com.example.donutapptest.data.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

import android.util.Log

class DonutViewModel : ViewModel() {
    private val _donuts = MutableLiveData<List<Donut>>()
    val donuts: LiveData<List<Donut>> = _donuts

    private val donutApi = RetrofitService.getRetrofitService().create(DonutApi::class.java)

    fun fetchDonuts() {
        viewModelScope.launch {
            try {
                val response = donutApi.getDonuts().awaitResponse()
                if (response.isSuccessful) {
                    _donuts.postValue(response.body())
                    Log.d("DonutViewModel", "Donuts fetched successfully: ${response.body()}")
                } else {
                    Log.e("DonutViewModel", "Error fetching donuts: ${response.errorBody()?.string()}")
                    // Handle error
                }
            } catch (e: Exception) {
                Log.e("DonutViewModel", "Exception fetching donuts: ${e.message}", e)
                // Handle error
            }
        }
    }
}

