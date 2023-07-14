package com.ssafy.gumi_life_project.data.model

data class BoardItem(
    val boardNo: Int,
    val title: String,
    val content: String,
    val hit: Int,
    val createDate: String,
    val updateDate: String,
    val folder: String,
    val originName: String,
    val saveName: String,
    val writerId: Int,
    val writerName: String,
    val likesNum: String,
    val likeStatus: String
) {
    constructor() : this(0, "", "", 0, "", "", "", "", "", 0, "", "", "")
}

data class BoardListResponse(
    val boardList: List<BoardItem>,
    val message: String
)