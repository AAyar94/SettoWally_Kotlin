package com.settowally.settowally.ui.fragment.search_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.common.Constant.Companion.PER_PAGE_PHOTO_COUNTER
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.model.PhotosDataModel
import com.settowally.settowally.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
    private val repository: Repository,
) : ViewModel() {

    val selectedQuality: Flow<PhotoQuality> = dataStoreRepository.savedQualitySetting
    val searchPhotoResponse = MutableLiveData<NetworkResponseHandler<PhotosDataModel>>()

    fun searchPhotosWithQuery(query: String, page: Int) {
        viewModelScope.launch {
            val response =
                repository.getSearchedPhotosFromRemote(query, page, PER_PAGE_PHOTO_COUNTER)
            searchPhotoResponse.postValue(response)
        }
    }

    fun savePhotoToDb(photo: Photo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePhotoToDb(photo)
        }
    }
}