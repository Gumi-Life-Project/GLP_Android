package com.ssafy.gumi_life_project.data.repository.board

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.data.remote.ApiService
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


class BoardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BoardRepository {
    override suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse> {
        return apiService.getBoardList()
    }

    override suspend fun getBoardDetail(boardNo: String): NetworkResponse<BoardDetailResponse, ErrorResponse> {
        return apiService.getBoardDetail(boardNo)
    }

    override suspend fun writeComment(commentDto: CommentDto): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.writeComment(commentDto)
    }

    override suspend fun writeReply(replyDto: ReplyDto): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.writeReply(replyDto)
    }

    override suspend fun writeBoard(
        boardDto: RequestBody,
        files: MutableList<MultipartBody.Part>?
    ): NetworkResponse<BoardWriteResponse, ErrorResponse> {
        return apiService.writeBoard(boardDto, files)

    }

    override suspend fun updateLike(boardNo: String): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.updateLike(boardNo)
    }

    override suspend fun deleteLike(boardNo: String): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.deleteLike(boardNo)
    }
}