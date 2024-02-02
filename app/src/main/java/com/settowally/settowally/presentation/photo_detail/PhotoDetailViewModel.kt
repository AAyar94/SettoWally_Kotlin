package com.settowally.settowally.presentation.photo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.domain.preferences.Preferences
import com.settowally.settowally.domain.usecase.GetSinglePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getSinglePhotoUseCase: GetSinglePhotoUseCase,
    private val preferences: Preferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getPhotoById(photoId: Int) {
        viewModelScope.launch {
            val response = getSinglePhotoUseCase.invoke(photoId)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    photo = response.data,
                    selectedQualityLink = when (preferences.readQualitySettings()) {
                        "landscape" -> response.data?.linkSource?.landscape
                        "large" -> response.data?.linkSource?.large
                        "large2x" -> response.data?.linkSource?.large2x
                        "medium" -> response.data?.linkSource?.medium
                        "original" -> response.data?.linkSource?.original
                        "portrait" -> response.data?.linkSource?.portrait
                        "small" -> response.data?.linkSource?.small
                        "tiny" -> response.data?.linkSource?.tiny
                        else -> response.data?.linkSource?.medium
                    }
                )
            }
        }

    }
}
