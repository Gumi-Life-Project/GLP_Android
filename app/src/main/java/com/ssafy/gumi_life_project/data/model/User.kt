package com.ssafy.gumi_life_project.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val email : String,
    val id : Int,
    val kakaoId : Int,
    val nickname : String,
    val oauthProvider : String,
    val thumbnail_image_url : String,
    @SerializedName("usernickname")
    val userNickname : String,
    val valid : Int
)
