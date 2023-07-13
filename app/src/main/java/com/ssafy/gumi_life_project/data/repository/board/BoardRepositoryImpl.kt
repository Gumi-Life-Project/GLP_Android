package com.ssafy.gumi_life_project.data.repository.board

import com.ssafy.gumi_life_project.data.model.BoardItem
import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.data.remote.ApiService
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BoardRepository {
    override suspend fun getBoardList(): NetworkResponse<List<BoardItem>, ErrorResponse> {
        return apiService.getBoardList()
    }
}