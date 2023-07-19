package com.ssafy.gumi_life_project.data.repository.user

import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.data.model.User
import com.ssafy.gumi_life_project.data.remote.ApiService
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getUserInfo(accessToken: String): NetworkResponse<User, ErrorResponse> {
        return apiService.getUserInfo(accessToken)
    }
}