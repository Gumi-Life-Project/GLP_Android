package com.ssafy.gumi_life_project.data.model

data class Member(
    val id: Int,
    val email: String,
    val nickname: String,
    val usernickname: String?,
    val thumbnail_image_url: String,
    val kakaoId: Long,
    val valid: Int,
    val oauthProvider: String
) {
    constructor() : this(0, "", "", "", "", 0, 0, "")
}
