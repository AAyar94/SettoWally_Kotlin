package com.settowally.settowally.ui.fragment.home_fragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.common.Constant
import com.settowally.settowally.common.Constant.Companion.PER_PAGE_PHOTO_COUNTER
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.local.data_store.DataStoreRepository
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotoQuality
import com.settowally.settowally.data.repository.Repository
import com.settowally.settowally.data.model.PhotosDataModel
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application = Application()) {

    val photoDataObject = MutableLiveData<NetworkResponseHandler<PhotosDataModel>>()
    val localDbResponse = MutableLiveData<List<Photo>>()
    val searchPhotoResponse = MutableLiveData<NetworkResponseHandler<PhotosDataModel>>()

    fun getAllSavedPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val localResponse = repository.getAllPhotosFromDb()
            localDbResponse.postValue(localResponse)
        }
    }

    fun getPhotos(page: Int, perPage: Int = PER_PAGE_PHOTO_COUNTER) {
        viewModelScope.launch {
            val photosResponse = repository.getPhotosFromRemote(page, perPage)
            photoDataObject.postValue(photosResponse)
        }
    }


    fun searchPhotosWithQuery(query: String, page: Int) {
        viewModelScope.launch {
            val response =
                repository.getSearchedPhotosFromRemote(query, page, Constant.PER_PAGE_PHOTO_COUNTER)
            searchPhotoResponse.postValue(response)
        }
    }
}