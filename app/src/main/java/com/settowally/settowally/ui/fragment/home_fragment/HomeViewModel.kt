package com.settowally.settowally.ui.fragment.home_fragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var networkStatus = false
    var backOnline = false


    fun getAllSavedPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val localResponse = repository.getAllPhotosFromDb()
            localDbResponse.postValue(localResponse)
        }
    }

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

    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveOnlineStatus(backOnline)
        }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We're back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }


}