package com.settowally.settowally.ui.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.common.Constant.Companion.PER_PAGE_PHOTO_COUNTER
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotosDataModel
import com.settowally.settowally.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val photoDataObject = MutableLiveData<NetworkResponseHandler<PhotosDataModel>>()
    private val localDbResponse = MutableLiveData<List<Photo>>()
    val searchPhotoResponse = MutableLiveData<NetworkResponseHandler<PhotosDataModel>>()


    /*fun getAllSavedPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val localResponse = repository.getAllPhotosFromDb()
            localDbResponse.postValue(localResponse)
        }
    }*/

    fun getPhotos(page: Int, perPage: Int = PER_PAGE_PHOTO_COUNTER) {
        viewModelScope.launch {
            val photosResponse = repository.getPhotosFromRemote(page, perPage)
            photoDataObject.postValue(photosResponse)
        }
    }


    fun searchPhotosWithQuery(query: String, page: Int) {
        if (query.isNotBlank()) {
            viewModelScope.launch {
                val response =
                    repository.getSearchedPhotosFromRemote(
                        query,
                        page,
                        PER_PAGE_PHOTO_COUNTER
                    )
                searchPhotoResponse.postValue(response)
            }
        }
    }
}