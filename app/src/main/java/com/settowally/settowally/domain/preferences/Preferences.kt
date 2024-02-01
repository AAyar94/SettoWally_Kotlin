package com.settowally.settowally.domain.preferences

interface Preferences {

    suspend fun saveQualitySettings(str: String)

    suspend fun readQualitySettings(): String


    companion object{
        const val KEY_QUALITY="key_quality"
    }
}