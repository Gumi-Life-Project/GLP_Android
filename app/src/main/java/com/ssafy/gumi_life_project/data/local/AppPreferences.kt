package com.ssafy.gumi_life_project.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object AppPreferences {
    private const val LOGIN_SESSION = "login.session"
    private const val SHUTTLE_BUS_INFO_PREFERENCE = "shuttle_bus_info_preference"

    const val APP_RUN_STATE = "app_run_state"

    private lateinit var preferences: SharedPreferences

    fun openSharedPrep(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun openShuttleBusSharedPreference(context: Context): SharedPreferences {
        preferences =
            context.getSharedPreferences(SHUTTLE_BUS_INFO_PREFERENCE, Context.MODE_PRIVATE)
        return preferences
    }

    fun updateAppRunState() {//앱 최초 실행이 아님을 표시
        preferences.edit().putBoolean(APP_RUN_STATE, false)
    }


}