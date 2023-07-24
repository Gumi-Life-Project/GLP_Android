package com.ssafy.gumi_life_project.data.model

data class Report(
    val boardNo: String,
    var report: String,
    val reportedId: String
)

data class ReportResponse(
    val message: String,
    val newReport: Report
)