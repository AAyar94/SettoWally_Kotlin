package com.settowally.settowally.data.preferences

import android.content.SharedPreferences
import com.settowally.settowally.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
) : Preferences {

    override suspend fun saveQualitySettings(str: String) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_QUALITY, str)
            .apply()
    }

    override suspend fun readQualitySettings(): String {
        return sharedPreferences.getString(Preferences.KEY_QUALITY, "medium")!!
    }
}