package com.ssafy.gumi_life_project.data.repository.user

import com.ssafy.gumi_life_project.data.model.ErrorResponse
import com.ssafy.gumi_life_project.data.model.Member
import com.ssafy.gumi_life_project.data.model.User
import com.ssafy.gumi_life_project.data.model.UserResponse
import com.ssafy.gumi_life_project.data.remote.ApiService
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getMemberInfo(accessToken: String): NetworkResponse<Member, ErrorResponse> {
        return apiService.getMemberInfo(accessToken)
    }

    override suspend fun getJwtToken(accessToken: String): NetworkResponse<UserResponse, ErrorResponse> {
        return apiService.getJwtToken(accessToken)
    }

    override suspend fun makeNickName(accessToken: String): NetworkResponse<Member, ErrorResponse> {
        return apiService.makeNickName(accessToken)
    }
}