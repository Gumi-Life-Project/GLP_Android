package com.ssafy.gumi_life_project.data.model

data class Tip(
    val id: Int,
    val subject: String,
    val description: String,
    val good: Int
) {
    constructor() : this(0, "", "", 0)
}
