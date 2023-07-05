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