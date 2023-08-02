package com.settowally.settowally.ui.fragment.favorites_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val localDbResponse = MutableLiveData<List<Photo>>()

    fun getAllSavedPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val localResponse = repository.getAllPhotosFromDb()
            localDbResponse.postValue(localResponse)
        }
    }

}