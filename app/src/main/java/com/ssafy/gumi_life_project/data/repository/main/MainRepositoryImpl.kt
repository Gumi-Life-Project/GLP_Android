package com.ssafy.gumi_life_project.data.repository.main

import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.data.model.Tip
import com.ssafy.gumi_life_project.data.model.WeatherResponse
import com.ssafy.gumi_life_project.data.remote.ApiService
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MainRepository {
    override suspend fun getAllTipList(): NetworkResponse<List<Tip>, ErrorResponse> {
        return apiService.getAllTipList()
    }

    override suspend fun getNowWeather(): NetworkResponse<WeatherResponse, ErrorResponse> {
        return apiService.getNowWeather()
    }

}
