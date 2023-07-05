package com.ssafy.gumi_life_project.data.model

import com.google.gson.annotations.SerializedName

data class Tip(
    val id : Int,
    val subject : String,
    @SerializedName("discription")
    val description : String,
    val good : Int
) {
    constructor() : this(0, "", "", 0)
}
