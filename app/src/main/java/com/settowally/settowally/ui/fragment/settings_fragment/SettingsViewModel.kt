package com.settowally.settowally.ui.fragment.settings_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    val selectedTheme: Flow<Theme> = dataStoreRepository.selectedThemeFlow

    fun saveTheme(theme: Theme) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDarkModeOption(theme)
        }
    }

    fun setPhotosQuality(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveQualityOption(text)
        }
    }
}