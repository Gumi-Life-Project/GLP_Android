package com.ssafy.gumi_life_project.data.repository.board

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body

interface BoardRepository {
    suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse>
    suspend fun getBoardDetail(boardNo: String): NetworkResponse<BoardDetailResponse, ErrorResponse>

    suspend fun writeComment(commentDto: CommentDto): NetworkResponse<CommentResponse, ErrorResponse>

    suspend fun writeReply(replyDto: ReplyDto): NetworkResponse<CommentResponse, ErrorResponse>

    suspend fun writeBoard(
        boardDto: RequestBody,
        files: MutableList<MultipartBody.Part>?
    ): NetworkResponse<BoardWriteResponse, ErrorResponse>

}