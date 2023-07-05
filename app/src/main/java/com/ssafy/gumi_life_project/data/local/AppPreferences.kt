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
}