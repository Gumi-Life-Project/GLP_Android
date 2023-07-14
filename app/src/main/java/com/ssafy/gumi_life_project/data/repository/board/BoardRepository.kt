package com.ssafy.gumi_life_project.data.repository.board

import com.ssafy.gumi_life_project.data.model.BoardItem
import com.ssafy.gumi_life_project.data.model.BoardListResponse
import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.util.network.NetworkResponse

interface BoardRepository {
    suspend fun getBoardList(): NetworkResponse<BoardListResponse, ErrorResponse>
}