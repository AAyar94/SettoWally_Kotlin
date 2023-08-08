package com.settowally.settowally.ui.fragment.settings_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.PhotoQuality
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
    val selectedQuality : Flow<PhotoQuality> = dataStoreRepository.savedQualitySetting

    fun saveTheme(theme: Theme) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDarkModeOption(theme)
        }
    }

    fun setPhotosQuality(quality: PhotoQuality) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveQualityOption(quality)
        }
    }

}