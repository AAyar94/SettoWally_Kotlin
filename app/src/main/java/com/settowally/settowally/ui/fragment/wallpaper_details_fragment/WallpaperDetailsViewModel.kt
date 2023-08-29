package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class WallpaperDetailsViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
    private val repository: Repository,
) : ViewModel() {

    val selectedQuality: Flow<PhotoQuality> = dataStoreRepository.savedQualitySetting
    val localDbResponse = MutableLiveData<List<Photo>>()

    fun getFavoritePhotos() {
        viewModelScope.launch {
            val favoritesResponse = repository.getAllPhotosFromDb()
            localDbResponse.postValue(favoritesResponse)
        }
    }

    fun isPhotoLiked(photo: Photo): Boolean {
        return runBlocking {
            delay(300)
            val list = repository.getAllPhotosFromDb()
            list.contains(photo) ?: false
        }
    }

    fun savePhotoToDb(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePhotoToDb(photo)
        }
    }

    fun deletePhotoFromDb(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePhotoFromDb(photo = photo)
        }
    }

}