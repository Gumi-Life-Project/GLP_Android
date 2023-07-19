package com.ssafy.gumi_life_project.data.repository.main

import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.data.model.MealResponse
import com.ssafy.gumi_life_project.data.model.Tip
import com.ssafy.gumi_life_project.data.model.WeatherResponse
import com.ssafy.gumi_life_project.util.network.NetworkResponse

interface MainRepository {
    suspend fun getAllTipList(): NetworkResponse<List<Tip>, ErrorResponse>

    suspend fun getNowWeather(): NetworkResponse<WeatherResponse, ErrorResponse>

    suspend fun getMealList() : NetworkResponse<MealResponse, ErrorResponse>
}