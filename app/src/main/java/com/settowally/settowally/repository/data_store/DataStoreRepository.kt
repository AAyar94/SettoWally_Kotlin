package com.settowally.settowally.repository.data_store

import android.content.Context
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)


@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val darkModeSetting = stringPreferencesKey(PREFERENCE_DARK_MODE_SETTING)
        val qualitySetting = stringPreferencesKey(PREFERENCE_QUALITY_SETTING)
    }

    private val dataStore: DataStore<Preferences> = context.datastore


    suspend fun saveDarkModeOption(str: String) {
        context.datastore.edit { preferences ->
            preferences[PreferenceKeys.darkModeSetting] = str
        }
    }

    suspend fun saveQualityOption(str: String) {
        context.datastore.edit { preferences ->
            preferences[PreferenceKeys.qualitySetting] = str
        }
    }

    val darkModeSavedOption: Flow<String> = context.datastore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val selectedDarkModeOption = preferences[PreferenceKeys.darkModeSetting] ?: "OS"
        selectedDarkModeOption
    }

    val savedQualitySetting: Flow<String> = context.datastore.data.catch { exception ->
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