package com.ssafy.gumi_life_project.data.remote

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("/tip/list")
    suspend fun getAllTipList(): NetworkResponse<List<Tip>, ErrorResponse>

    @GET("/weather/")
    suspend fun getNowWeather(): NetworkResponse<WeatherResponse, ErrorResponse>

    @GET("/board/list")
    suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse>

    @GET("/board/list/new")
    suspend fun getThreeBoard(): NetworkResponse<BoardListResponse, ErrorResponse>

    @GET("/board/{boardNo}")
    suspend fun getBoardDetail(@Path("boardNo") boardNo: String): NetworkResponse<BoardDetailResponse, ErrorResponse>

    @Multipart
    @POST("/board/writeBoard")
    suspend fun writeBoard(
        @Part("boardDto") boardDto: RequestBody,
        @Part files: MutableList<MultipartBody.Part>?
    ): NetworkResponse<BoardWriteResponse, ErrorResponse>

    @GET("/meal/")
    suspend fun getMealList(): NetworkResponse<MealResponse, ErrorResponse>

    @POST("/board/writeComment")
    suspend fun writeComment(@Body commentDto: CommentDto): NetworkResponse<CommentResponse, ErrorResponse>

    @PUT("/board/modifyComment")
    suspend fun modifyComment(): NetworkResponse<CommentResponse, ErrorResponse>

    @PUT("/board/deleteComment")
    suspend fun deleteComment(): NetworkResponse<CommentResponse, ErrorResponse>

    @PUT("/board/modifyBoard")
    suspend fun modifyBoard(
        @Part("boardDto") boardDto: RequestBody,
        @Part files: MutableList<MultipartBody.Part>?
    ): NetworkResponse<BoardModifyResponse, ErrorResponse>
}