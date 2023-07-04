package com.ssafy.gumi_life_project.data.local

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.gumi_life_project.App
import com.ssafy.gumi_life_project.data.model.TriggerTime
import java.util.*

object AppPreferences {
    private const val LOGIN_SESSION = "login.session"

     fun openSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun saveTriggerTimes(triggerTimes: List<TriggerTime>) {
        val sharedPreferences = App.prefs
        val editor = sharedPreferences.edit()

        for (i in triggerTimes.indices) {
            val triggerTime = triggerTimes[i]
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.set(Calendar.YEAR, triggerTime.year)
            calendar.set(Calendar.MONTH, triggerTime.month)
            calendar.set(Calendar.DAY_OF_MONTH, triggerTime.day)
            calendar.set(Calendar.HOUR_OF_DAY, triggerTime.hour)
            calendar.set(Calendar.MINUTE, triggerTime.minute)
            calendar.set(Calendar.SECOND, triggerTime.second)
            calendar.set(Calendar.MILLISECOND, 0)
            val utcTimeSeconds = calendar.timeInMillis / 1000 // 초 단위 UTC 시간 계산
            editor.putLong(getTriggerTimeKey(i), utcTimeSeconds)
        }

        editor.apply()
    }

    fun loadTriggerTimes(): List<Long> {
        val sharedPreferences = App.prefs
        val triggerTimes = mutableListOf<Long>()

        for (i in 0 until TRIGGER_COUNT) {
            val utcTimeSeconds = sharedPreferences.getLong(getTriggerTimeKey(i), 1688378295)
            triggerTimes.add(utcTimeSeconds)
        }

        return triggerTimes
    }

    private const val TRIGGER_COUNT = 3

    private fun getTriggerTimeKey(triggerNumber: Int) = "trigger_${triggerNumber}_time"
}