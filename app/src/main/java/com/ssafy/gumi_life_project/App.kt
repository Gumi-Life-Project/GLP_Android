package com.ssafy.gumi_life_project

import android.app.Application
import android.content.SharedPreferences
import com.ssafy.gumi_life_project.data.local.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var prefs : SharedPreferences
    }

    override fun onCreate() {
        prefs = AppPreferences.openSharedPrep(applicationContext)
        super.onCreate()
    }
}