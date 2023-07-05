package com.ssafy.gumi_life_project.util

object CrossWorkTimeList {
    private val timeList: MutableList<String> = mutableListOf()

    fun setTimeList(list: List<String>) {
        timeList.clear()
        timeList.addAll(list)
    }

    fun getTimeList(): List<String> {
        return timeList
    }
}

private fun generateCrossWorkTimeList(): List<String> {
    val baseTime = 6 * 3600 + 55 * 60 + 16
    val interval = 3 * 60
    val greenDuration = 25
    val redDuration = 155

    val timeList = mutableListOf<String>()
    for (i in 0 until 24 * 60 * 60) {
        val elapsedSeconds = i * interval
        val greenTime = baseTime + elapsedSeconds
        var redTime = greenTime + greenDuration
        if (redTime % interval == 0) {
            redTime -= interval
        }
        val formattedTime = formatTime(redTime % (24 * 60 * 60))
        timeList.add(formattedTime)
    }

    return timeList
}

fun getCrossWorkTimeListWithRecentTime(currentTime: String): List<String> {
    val timeList = CrossWorkTimeList.getTimeList()
    var currentIndex = 0
    for (index in 1 until timeList.size) {
        if (timeList[index - 1] <= currentTime && timeList[index] >= currentTime) {
            currentIndex = index
            break
        } else if (timeList[index] == currentTime) {
            currentIndex = index
            break
        }
    }
    val endIndex = minOf(currentIndex + 10, timeList.size - 1)
    return timeList.subList(currentIndex, endIndex + 1)
}

fun setCrossWorkTimeList() {
    val timeList = generateCrossWorkTimeList()
    CrossWorkTimeList.setTimeList(timeList)
}

private fun formatTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    return String.format("%02d:%02d", hours, minutes)
}
