package com.majazeh.emall.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private lateinit var preferences: SharedPreferences
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)
    private val AUTH = Pair("AUTH", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences("NAME", Context.MODE_PRIVATE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var firstRun: Boolean
        get() = preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }

    var auth: String
        get() = preferences.getString(AUTH.first, AUTH.second) ?: ""
        set(value) = preferences.edit {
            it.putString(AUTH.first, value)
        }
}