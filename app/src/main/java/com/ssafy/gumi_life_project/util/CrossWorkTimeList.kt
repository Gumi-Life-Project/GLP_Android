package com.ssafy.gumi_life_project.util

import com.ssafy.gumi_life_project.data.model.SignalLight
import com.ssafy.gumi_life_project.data.model.TriggerTime

object CrossWorkTimeList {
    private val triggerTimes = setTriggerTimes()
    private val signalLight1TimeList =
        generateCrossWorkTimeList(SignalLight.SIGNAL_LIGHT_1, triggerTimes[0])
    private val signalLight2TimeList =
        generateCrossWorkTimeList(SignalLight.SIGNAL_LIGHT_2, triggerTimes[1])
    private val signalLight3TimeList =
        generateCrossWorkTimeList(SignalLight.SIGNAL_LIGHT_3, triggerTimes[2])

    fun getSignalLight1TimeList() = signalLight1TimeList
    fun getSignalLight2TimeList() = signalLight2TimeList
    fun getSignalLight3TimeList() = signalLight3TimeList

    private fun setTriggerTimes(): List<TriggerTime> {
        val list = mutableListOf<TriggerTime>()
        list.add(TriggerTime(8, 51, 51))
        list.add(TriggerTime(8, 59, 42))
        list.add(TriggerTime(8, 52, 25))
        return list
    }

    fun getTriggerTimes(): List<TriggerTime> {
        return triggerTimes
    }
}

private fun generateCrossWorkTimeList(signalLight: SignalLight, time: TriggerTime): List<Int> {
    val baseTime = time.hour * 3600 + time.minute * 60 + time.second
    val greenDuration = signalLight.greenDuration
    val redDuration = signalLight.redDuration
    val interval = greenDuration + redDuration

    val timeList = mutableListOf<Int>()
    for (i in 0 until 480) {
        val elapsedSeconds = i * interval
        val greenTime = baseTime + elapsedSeconds
        timeList.add(greenTime % (24 * 60 * 60))
    }
    return timeList
}

fun getCrossWorkTimeListWithRecentTime(
    signalLight: SignalLight,
    currentTime: Int
): List<String> {
    val timeList = when (signalLight) {
        SignalLight.SIGNAL_LIGHT_1 -> CrossWorkTimeList.getSignalLight1TimeList()
        SignalLight.SIGNAL_LIGHT_2 -> CrossWorkTimeList.getSignalLight2TimeList()
        SignalLight.SIGNAL_LIGHT_3 -> CrossWorkTimeList.getSignalLight3TimeList()
    }
    var currentIndex = 0
    for (index in timeList.indices) {
        if (timeList[index] >= currentTime) {
            currentIndex = index
            break
        }
    }

    val endIndex = minOf(currentIndex + 10, timeList.size - 1)
    return timeList.subList(currentIndex, endIndex + 1).map { formatTime(it) }
}


private fun formatTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    return String.format("%02d시 %02d분", hours, minutes)
}
