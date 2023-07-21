package com.ssafy.gumi_life_project.data.model

data class UserResponse(
    val message: String,
    val user: User
)

data class User(
    val jwt: Jwt,
    val isPresent: Boolean
)

data class Jwt(
    val accessToken: String,
    val refreshToken: String,
    val grantType: String,
    val expiresIn: Int
)