package com.ssafy.gumi_life_project

import android.app.Application
import android.content.SharedPreferences
import com.ssafy.gumi_life_project.data.local.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var prefs: SharedPreferences
        lateinit var shuttleBusPrefs: SharedPreferences
    }

    override fun onCreate() {
        prefs = AppPreferences.openSharedPrep(applicationContext)

        shuttleBusPrefs = AppPreferences.openShuttleBusSharedPreference(applicationContext)
        if (shuttleBusPrefs.getBoolean(AppPreferences.APP_RUN_STATE, true)) {
            AppPreferences.updateAppRunState()
        }

        super.onCreate()
    }
}