package com.ssafy.gumi_life_project.data.repository.board

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Query

interface BoardRepository {
    suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse>
    suspend fun getBoardDetail(boardNo: String): NetworkResponse<BoardDetailResponse, ErrorResponse>

    suspend fun writeComment(commentDto: CommentDto): NetworkResponse<BaseResponse, ErrorResponse>

    suspend fun writeReply(replyDto: ReplyDto): NetworkResponse<BaseResponse, ErrorResponse>

    suspend fun writeBoard(
        boardDto: RequestBody,
        files: MutableList<MultipartBody.Part>?
    ): NetworkResponse<BoardWriteResponse, ErrorResponse>

    suspend fun modifyBoard(
        boardDto: RequestBody,
        files: MutableList<MultipartBody.Part>?
    ): NetworkResponse<BoardModifyResponse, ErrorResponse>

    suspend fun deleteBoard(boardNo: String, boardWriterId: String): NetworkResponse<BaseResponse, ErrorResponse>

    suspend fun updateLike(boardNo: String): NetworkResponse<BaseResponse, ErrorResponse>

    suspend fun deleteLike(boardNo: String): NetworkResponse<BaseResponse, ErrorResponse>
}