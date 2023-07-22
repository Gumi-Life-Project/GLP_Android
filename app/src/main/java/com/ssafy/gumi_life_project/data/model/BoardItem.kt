package com.ssafy.gumi_life_project.data.model

data class BoardItem(
    val boardNo: String,
    val title: String,
    val content: String,
    val hit: Int,
    val createDate: String,
    val updateDate: String?,
    val folder: String,
    val originName: String,
    val saveName: String,
    val writerId: Int,
    val writerName: String,
    val likesNum: Int,
    val likeStatus: Int
)

data class BoardListResponse(
    val boardList: List<BoardItem>,
    val message: String
)

data class BoardDetailResponse(
    val boardDetail: BoardItem,
    val comments: List<Comment>,
    val message: String
)

data class BoardModifyResponse(
    val message: String
)