package com.ssafy.gumi_life_project.data.remote

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/tip/list")
    suspend fun getAllTipList(): NetworkResponse<List<Tip>, ErrorResponse>

    @GET("/weather/")
    suspend fun getNowWeather(): NetworkResponse<WeatherResponse, ErrorResponse>

    @GET("/api/admin/{accessToken}")
    suspend fun getMemberInfo(@Header("Authorization") accessToken: String) : NetworkResponse<Member, ErrorResponse>

    @GET("/board/list")
    suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse>

    @GET("/board/list/new")
    suspend fun getThreeBoard(): NetworkResponse<BoardListResponse, ErrorResponse>

    @GET("/meal/")
    suspend fun getMealList(): NetworkResponse<MealResponse, ErrorResponse>

    @POST("/api/auth/kakaomobile")
    suspend fun getJwtToken(@Body accessToken: String) : NetworkResponse<UserResponse, ErrorResponse>

    @PUT("/api/members/makenickName")
    suspend fun makeNickName(@Header("Authorization") accessToken: String) : NetworkResponse<Member, ErrorResponse>
}