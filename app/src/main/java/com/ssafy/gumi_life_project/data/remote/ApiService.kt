package com.ssafy.gumi_life_project.data.remote

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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

    @GET("/board/list/myboards")
    suspend fun getUserBoards(@Header("Authorization") Authorization: String) : NetworkResponse<BoardListResponse, ErrorResponse>

    @GET("/board/list/mycomments")
    suspend fun getUserComments(@Header("Authorization") Authorization: String) : NetworkResponse<BoardListResponse, ErrorResponse>

    @GET("/board/list/likes")
    suspend fun getUserLikes(@Header("Authorization") jwtToken: String) : NetworkResponse<BoardListResponse, ErrorResponse>
}