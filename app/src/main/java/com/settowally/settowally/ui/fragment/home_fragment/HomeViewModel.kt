package com.settowally.settowally.ui.fragment.home_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.repository.Repository
import com.settowally.settowally.data.model.PhotosDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val photoDataObject = MutableLiveData<NetworkResponseHandler<PhotosDataModel>>()

    fun getPhotos(page: Int, perPage: Int) {
        viewModelScope.launch {
            val photosResponse = repository.getPhotosFromRemote(page, perPage)
            photoDataObject.postValue(photosResponse)
        }
    }

    fun savePhotoToDb(photo: Photo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePhotoToDb(photo)
        }
    }

}