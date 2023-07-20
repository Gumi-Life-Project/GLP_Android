package com.ssafy.gumi_life_project.data.model

data class MealResponse(val data : List<Meal>, val message: String)

data class Meal(val id: String, val modifiedAt: String, val title : String, val time: String, val menu : String, val kcal: String, val course: String, val photoURL: String?) {
    val courseFormatted: String
    get() {
        val courseList = menu.split(",").map { it.trim() }
        return courseList.joinToString("\n")
    }
}