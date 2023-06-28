package com.ssafy.gumi_life_project.data.local

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val LOGIN_SESSION = "login.session"

    fun openSharedPrep(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }
}