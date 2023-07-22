package com.ssafy.gumi_life_project.data.repository.user

import com.ssafy.gumi_life_project.data.model.*
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

    override suspend fun getUserBoards(jwtToken: String): NetworkResponse<BoardListResponse, ErrorResponse> {
        return apiService.getUserBoards(jwtToken)
    }

    override suspend fun getUserComments(jwtToken: String): NetworkResponse<BoardListResponse, ErrorResponse> {
        return apiService.getUserComments(jwtToken)
    }

    override suspend fun getUserLikes(jwtToken: String): NetworkResponse<BoardListResponse, ErrorResponse> {
        return apiService.getUserLikes(jwtToken)
    }
}