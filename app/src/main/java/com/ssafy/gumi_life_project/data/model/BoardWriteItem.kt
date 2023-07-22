package com.ssafy.gumi_life_project.data.model

data class BoardWriteItem(
    var title: String,
    var content: String
)

data class BoardWriteResponse(
    var message: String
)

enum class BoardWriteResponseType {
    SUCCESS, FAIL
}