package com.ssafy.gumi_life_project.data.repository.user

import com.ssafy.gumi_life_project.data.model.*
import com.ssafy.gumi_life_project.util.network.NetworkResponse
import retrofit2.http.Header

interface UserRepository {
    // 유저의 jwt 토큰을 파라미터로 받아서 member에 대한 객체 정보를 반환
    suspend fun getMemberInfo(accessToken: String): NetworkResponse<Member, ErrorResponse>

    suspend fun getJwtToken(accessToken: String): NetworkResponse<UserResponse, ErrorResponse>

    suspend fun makeNickName(@Header("Authorization") accessToken: String) : NetworkResponse<Member, ErrorResponse>

    suspend fun getUserBoards(@Header("Authorization") jwtToken: String) : NetworkResponse<BoardListResponse, ErrorResponse>

    suspend fun getUserComments(@Header("Authorization") jwtToken: String) : NetworkResponse<BoardListResponse, ErrorResponse>

    suspend fun getUserLikes(@Header("Authorization") jwtToken: String) : NetworkResponse<BoardListResponse, ErrorResponse>
}