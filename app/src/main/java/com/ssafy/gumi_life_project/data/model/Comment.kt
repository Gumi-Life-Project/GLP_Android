package com.ssafy.gumi_life_project.data.model

data class CommentDto(val boardNo: String, val content: String)

data class ReplyDto(val boardNo: String, val commentNo:String, val content: String)

data class Comment(
    val commentNo: String,
    val content: String,
    val boardNo: String?, // boardNo가 nullable한 경우를 고려
    val writerId: String,
    val writerName: String?,
    val createDate: String,
    val writerImg: String?,
    val status: Int,
    val replyList: List<Reply>
)

data class Reply(
    val replyNo: String,
    val commentNo: String,
    val boardNo: String?, // boardNo가 nullable한 경우를 고려
    val writerId: String,
    val writerName: String?,
    val writerImg: String?,
    val createDate: String,
    val content: String,
    val status: Int
)