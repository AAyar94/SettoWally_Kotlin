package com.settowally.settowally.ui.fragment.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.model.Theme
import com.settowally.settowally.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val repository: Repository
) : ViewModel() {

    val selectedTheme: Flow<Theme> = dataStoreRepository.selectedThemeFlow
    val selectedQuality: Flow<PhotoQuality> = dataStoreRepository.savedQualitySetting
    private val recentlyDeletedPhotos = mutableListOf<Photo>()


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

    fun deleteAllFavoritePhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoritePhotoList = repository.getAllPhotosFromDb()
            for (photo in favoritePhotoList) {
                recentlyDeletedPhotos.add(photo)
            }
            repository.deleteAllFavoritePhotos()
            delay(3000)
        }
    }

    fun restoreRecentlyDeletedPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            for (photo in recentlyDeletedPhotos) {
                repository.savePhotoToDb(photo)
            }
        }
    }


}