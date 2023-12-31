package com.ssafy.gumi_life_project.data.repository.home

import com.ssafy.gumi_life_project.data.model.BoardListResponse
import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.data.model.Tip
import com.ssafy.gumi_life_project.data.model.WeatherResponse
import com.ssafy.gumi_life_project.util.network.NetworkResponse

interface HomeRepository {

    suspend fun getSimpleBoard(): NetworkResponse<BoardListResponse, ErrorResponse>
}