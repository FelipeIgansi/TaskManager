package com.taskmanager.data

import android.content.Context
import android.content.SharedPreferences
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes

class SessionAuth(context: Context) {
    private val preference: SharedPreferences =
        context.getSharedPreferences("sessionPreferences", 0)

    fun saveAuthenticationStage(stage:String){
        preference.edit().putString(Constants.AUTHENTICATION.KEYSESSION, stage).apply()
    }

    fun getAuthentication(): String?{
        return preference.getString(Constants.AUTHENTICATION.KEYSESSION, Routes.WelcomeScreen.route)
    }
}