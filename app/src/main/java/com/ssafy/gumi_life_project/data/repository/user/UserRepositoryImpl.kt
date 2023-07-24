package com.ssafy.gumi_life_project.data.repository.user

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.data.remote.ApiService
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getMemberInfo(): NetworkResponse<Member, ErrorResponse> {
        return apiService.getMemberInfo()
    }

    override suspend fun getJwtToken(accessToken: String): NetworkResponse<UserResponse, ErrorResponse> {
        return apiService.getJwtToken(accessToken)
    }

    override suspend fun getUserId(): NetworkResponse<String, ErrorResponse> {
        return apiService.getUserId()
    }

    override suspend fun makeNickName(nickName : Nickname): NetworkResponse<Member, ErrorResponse> {
        return apiService.makeNickName(nickName)
    }

    override suspend fun getUserBoards(): NetworkResponse<BoardListResponse, ErrorResponse> {
        return apiService.getUserBoards()
    }

    override suspend fun getUserComments(): NetworkResponse<BoardListResponse, ErrorResponse> {
        return apiService.getUserComments()
    }

    override suspend fun getUserLikes(): NetworkResponse<BoardListResponse, ErrorResponse> {
        return apiService.getUserLikes()
    }
}