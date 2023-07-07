package com.ssafy.gumi_life_project.data.model

import androidx.annotation.ColorRes
import com.ssafy.gumi_life_project.R
import java.util.*


data class LightTime(val currentTrafficLightColor: TrafficLightColor, val remainingTime: Long) {
    //currentColor 현재 신호등 색, remainingTime 다음 신호등 색까지 남은 시간
    val remainingTimeString: String
        get() {
            val minutes = remainingTime / 60
            val seconds = remainingTime % 60
            return String.format("%02d:%02d", minutes, seconds)
        }

    @ColorRes
    fun getCurrentColorRes(): Int {
        return currentTrafficLightColor.colorRes
    }
}

data class TriggerTime(
    val hour: Int,
    val minute: Int,
    val second: Int
)

enum class TrafficLightColor(@ColorRes val colorRes: Int) {
    GREEN(R.color.green),
    RED(R.color.red)
}

enum class SignalLight(
    val titleResId: Int,
    val contentResId: Int,
    val greenDuration: Int,
    val redDuration: Int
) {
    SIGNAL_LIGHT_1(R.string.cross_walk_title1, R.string.cross_walk_explain1, 25, 155),
    SIGNAL_LIGHT_2(R.string.cross_walk_title2, R.string.cross_walk_explain2, 35, 145),
    SIGNAL_LIGHT_3(R.string.cross_walk_title3, R.string.cross_walk_explain3, 30, 150),
    SIGNAL_LIGHT_4(R.string.cross_walk_title1, R.string.cross_walk_explain1, 25, 175),
    SIGNAL_LIGHT_5(R.string.cross_walk_title2, R.string.cross_walk_explain2, 35, 165),
    SIGNAL_LIGHT_6(R.string.cross_walk_title3, R.string.cross_walk_explain3, 30, 170);

    fun calculateRemainingTime(triggerTime: TriggerTime): LightTime {
        val currentTimeMillis = System.currentTimeMillis()
        val triggerTimeMillis = getTimeInMillis(triggerTime)
        val elapsedTimeMillis = if (currentTimeMillis > triggerTimeMillis) {
            currentTimeMillis - triggerTimeMillis
        } else {
            (24 * 60 * 60 * 1000) - (triggerTimeMillis - currentTimeMillis)
        }
        val remainingTimeMillis = elapsedTimeMillis % ((greenDuration + redDuration) * 1000)

        return if (remainingTimeMillis > greenDuration * 1000) {
            LightTime(
                TrafficLightColor.RED,
                ((greenDuration * 1000 + redDuration * 1000) - remainingTimeMillis) / 1000
            )
        } else {
            LightTime(
                TrafficLightColor.GREEN,
                ((greenDuration * 1000) - remainingTimeMillis) / 1000
            )
        }
    }

    private fun getTimeInMillis(triggerTime: TriggerTime): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, triggerTime.hour)
        calendar.set(Calendar.MINUTE, triggerTime.minute)
        calendar.set(Calendar.SECOND, triggerTime.second)
        return calendar.timeInMillis
    }
}