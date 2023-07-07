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
    private val signalLight4TimeList =
        generateCrossWorkTimeList(SignalLight.SIGNAL_LIGHT_4, triggerTimes[3])
    private val signalLight5TimeList =
        generateCrossWorkTimeList(SignalLight.SIGNAL_LIGHT_5, triggerTimes[4])
    private val signalLight6TimeList =
        generateCrossWorkTimeList(SignalLight.SIGNAL_LIGHT_6, triggerTimes[5])

    fun getSignalLight1TimeList() = signalLight1TimeList
    fun getSignalLight2TimeList() = signalLight2TimeList
    fun getSignalLight3TimeList() = signalLight3TimeList
    fun getSignalLight4TimeList() = signalLight4TimeList
    fun getSignalLight5TimeList() = signalLight5TimeList
    fun getSignalLight6TimeList() = signalLight6TimeList

    private fun setTriggerTimes(): List<TriggerTime> {
        val list = mutableListOf<TriggerTime>()
        list.add(TriggerTime(18, 55, 15))
        list.add(TriggerTime(18, 59, 28))
        list.add(TriggerTime(19, 1, 53))
        list.add(TriggerTime(8, 51, 51))
        list.add(TriggerTime(8, 59, 42))
        list.add(TriggerTime(8, 52, 28))
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
    return timeList.sorted()
}

fun getCrossWorkTimeListWithRecentTime(
    signalLight: SignalLight,
    currentTime: Int
): List<String> {
    val timeList = when (signalLight) {
        SignalLight.SIGNAL_LIGHT_1 -> CrossWorkTimeList.getSignalLight1TimeList()
        SignalLight.SIGNAL_LIGHT_2 -> CrossWorkTimeList.getSignalLight2TimeList()
        SignalLight.SIGNAL_LIGHT_3 -> CrossWorkTimeList.getSignalLight3TimeList()
        SignalLight.SIGNAL_LIGHT_4 -> CrossWorkTimeList.getSignalLight4TimeList()
        SignalLight.SIGNAL_LIGHT_5 -> CrossWorkTimeList.getSignalLight5TimeList()
        SignalLight.SIGNAL_LIGHT_6 -> CrossWorkTimeList.getSignalLight6TimeList()
    }
    var currentIndex = 0
    for (index in 1 until timeList.size) {
        if (timeList[index] == currentTime) {
            currentIndex = index
            break
        } else if (timeList[index - 1] <= currentTime && timeList[index] >= currentTime) {
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
