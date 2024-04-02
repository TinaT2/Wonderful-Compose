package com.example.wonderfulcompose.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesPersistor @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0)

    companion object {
        const val SHARED_PREFERENCES_NAME = "Cat"
        const val USER_NAME = "Username"
    }

//    constructor(context: Context) : this(
//        context.getSharedPreferences(
//            SHARED_PREFERENCES_NAME,
//            0
//        )
//    )

    fun get(key: String): String? {
        return sharedPreferences.getString(key, null as String?)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0L)
    }

    fun save(key: String, value: String?): Boolean {
        val sharesPreferencesEditor = sharedPreferences.edit()
        sharesPreferencesEditor.putString(key, value)
        return sharesPreferencesEditor.commit()
    }
}