package com.settowally.settowally.ui.fragment.home_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.repository.Repository
import com.settowally.settowally.repository.remote.model.PhotosDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

}