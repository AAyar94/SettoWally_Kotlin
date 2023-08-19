package com.settowally.settowally.data.local.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.settowally.settowally.common.Constant.Companion.BACK_ONLINE
import com.settowally.settowally.common.Constant.Companion.PREFERENCES_NAME
import com.settowally.settowally.common.Constant.Companion.PREFERENCE_DARK_MODE_SETTING
import com.settowally.settowally.common.Constant.Companion.PREFERENCE_QUALITY_SETTING
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.model.Theme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
@Singleton
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedTheme = stringPreferencesKey(PREFERENCE_DARK_MODE_SETTING)
        val qualitySetting = stringPreferencesKey(PREFERENCE_QUALITY_SETTING)
        val backOnline = booleanPreferencesKey(BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.datastore

    /**     DARK MODE       */
    suspend fun saveDarkModeOption(theme: Theme) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedTheme] = theme.name
        }
    }

    val selectedThemeFlow: Flow<Theme> = dataStore.data.map { preferences ->
        val themeName = preferences[PreferenceKeys.selectedTheme] ?: Theme.SYSTEM.name
        try {
            Theme.valueOf(themeName)
        } catch (ex: IllegalArgumentException) {
            Log.e("Failed - -  -   -    -", "$ex")
            Theme.SYSTEM
        }
    }


    /**      SAVED QUALİTY       */
    suspend fun saveQualityOption(quality: PhotoQuality) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.qualitySetting] = quality.name
        }
    }

    val savedQualitySetting: Flow<PhotoQuality> = dataStore.data.map { preferences ->
        val qualityName = preferences[PreferenceKeys.qualitySetting] ?: PhotoQuality.MEDIUM.name
        try {
            PhotoQuality.valueOf(qualityName)
        } catch (e: java.lang.IllegalArgumentException) {
            Log.e("Failed - -  -   -    -", "$e")
            PhotoQuality.MEDIUM
        }
    }




    /**      BACK ONLINE     */
    val backOnlineFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        val onlineStatus = preferences[PreferenceKeys.backOnline] ?: false
        try {
            onlineStatus
        } catch (e: Exception) {
            false
        }
    }

    suspend fun saveOnlineStatus(status: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = status
        }
    }
}