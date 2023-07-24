package com.ssafy.gumi_life_project

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.gumi_life_project.data.local.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var prefs: SharedPreferences
        lateinit var shuttleBusPrefs: SharedPreferences
        private var appContext : Context? = null
    }

    override fun onCreate() {
        prefs = AppPreferences.openSharedPreferences(applicationContext)

        shuttleBusPrefs = AppPreferences.openShuttleBusSharedPreference(applicationContext)
        if (shuttleBusPrefs.getBoolean(AppPreferences.APP_RUN_STATE, true)) {
            AppPreferences.updateAppRunState()
            AppPreferences.initShuttleBusInfo()
        }
        appContext = this
        KakaoSdk.init(this, BuildConfig.API_KEY)
        super.onCreate()
    }
}