package com.ssafy.gumi_life_project.data.remote

import com.ssafy.gumi_life_project.data.model.BoardItem
import com.ssafy.gumi_life_project.data.model.BoardListResponse
import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.data.model.Tip
import com.ssafy.gumi_life_project.data.model.WeatherResponse
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/tip/list")
    suspend fun getAllTipList(): NetworkResponse<List<Tip>, ErrorResponse>

    @GET("/weather/")
    suspend fun getNowWeather(): NetworkResponse<WeatherResponse, ErrorResponse>

    @GET("/board/list")
    suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse>

    @GET("/board/list/new")
    suspend fun getThreeBoard(): NetworkResponse<BoardListResponse, ErrorResponse>
}