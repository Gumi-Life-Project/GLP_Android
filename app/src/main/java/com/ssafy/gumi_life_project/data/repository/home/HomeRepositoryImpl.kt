package com.ssafy.gumi_life_project.data.repository.home

import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.data.model.Tip
import com.ssafy.gumi_life_project.data.remote.ApiService
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {
    override suspend fun getAllTipList(): NetworkResponse<List<Tip>, ErrorResponse> {
        return apiService.getAllTipList()
    }

}