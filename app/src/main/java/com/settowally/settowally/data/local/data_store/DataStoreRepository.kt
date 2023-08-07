package com.settowally.settowally.data.local.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.settowally.settowally.common.Constant.Companion.PREFERENCES_NAME
import com.settowally.settowally.common.Constant.Companion.PREFERENCE_DARK_MODE_SETTING
import com.settowally.settowally.common.Constant.Companion.PREFERENCE_QUALITY_SETTING
import com.settowally.settowally.data.model.Theme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

// @ActivityScope yerine @Singleton yaptım. Hem Activity hem ViewModel'da çağırabiliyorum.
@Singleton
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedTheme = stringPreferencesKey(PREFERENCE_DARK_MODE_SETTING)
        val qualitySetting = stringPreferencesKey(PREFERENCE_QUALITY_SETTING)
    }

    private val dataStore: DataStore<Preferences> = context.datastore

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

    suspend fun saveQualityOption(str: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.qualitySetting] = str
        }
    }

    val savedQualitySetting: Flow<String> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val selectedQualitySetting = preferences[PreferenceKeys.qualitySetting] ?: "Medium"
        selectedQualitySetting
    }
}