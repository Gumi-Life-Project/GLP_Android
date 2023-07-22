package com.ssafy.gumi_life_project.data.repository.board

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface BoardRepository {
    suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse>
    suspend fun getBoardDetail(boardNo: String): NetworkResponse<BoardDetailResponse, ErrorResponse>

    suspend fun writeComment(@Body commentDto: CommentDto): NetworkResponse<CommentResponse, ErrorResponse>
}