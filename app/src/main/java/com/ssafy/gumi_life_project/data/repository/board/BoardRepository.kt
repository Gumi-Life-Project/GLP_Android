package com.ssafy.gumi_life_project.data.repository.board

import com.ssafy.gumi_life_project.data.model.BoardListResponse
import com.ssafy.gumi_life_project.data.model.BoardWriteResponse
import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface BoardRepository {
    suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse>
    suspend fun writeBoard(
        boardDto: RequestBody,
        files: MutableList<MultipartBody.Part>?
    ): NetworkResponse<BoardWriteResponse, ErrorResponse>
}