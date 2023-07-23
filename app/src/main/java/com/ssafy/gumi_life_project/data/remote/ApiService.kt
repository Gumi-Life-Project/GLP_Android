package com.ssafy.gumi_life_project.data.remote

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("/api/auth/findid")
    suspend fun findId(): NetworkResponse<Int, ErrorResponse>

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

    @PUT("/board/deleteBoard")
    suspend fun deleteBoard(
        @Query("boardNo") boardNo: String,
        @Query("BoardWriterId") boardWriterId: String
    ): NetworkResponse<BaseResponse, ErrorResponse>

    @GET("/meal/")
    suspend fun getMealList(): NetworkResponse<MealResponse, ErrorResponse>

    @POST("/board/writeComment")
    suspend fun writeComment(@Body commentDto: CommentDto): NetworkResponse<BaseResponse, ErrorResponse>

    @POST("/board/writeReply")
    suspend fun writeReply(@Body replyDto: ReplyDto): NetworkResponse<BaseResponse, ErrorResponse>

    @PUT("/board/modifyComment")
    suspend fun modifyComment(): NetworkResponse<BaseResponse, ErrorResponse>


    @PUT("/board/deleteComment")
    suspend fun deleteComment(@Query("commentNo") commentNo: String, @Query("commentWriterId") commentWriterId: String): NetworkResponse<BaseResponse, ErrorResponse>

    @PUT("/board/deleteReply")
    suspend fun deleteReply(@Query("replyNo") replyNo: String, @Query("replyWriterId") replyWriterId: String): NetworkResponse<BaseResponse, ErrorResponse>


    @PUT("/board/like")
    suspend fun updateLike(@Query("boardNo") boardNo: String): NetworkResponse<BaseResponse, ErrorResponse>

    @DELETE("/board/dislike")
    suspend fun deleteLike(@Query("boardNo") boardNo: String): NetworkResponse<BaseResponse, ErrorResponse>

    @Multipart
    @PUT("/board/modifyBoard")
    suspend fun modifyBoard(
        @Part("boardDto") boardDto: RequestBody,
        @Part files: MutableList<MultipartBody.Part>?
    ): NetworkResponse<BoardModifyResponse, ErrorResponse>
}