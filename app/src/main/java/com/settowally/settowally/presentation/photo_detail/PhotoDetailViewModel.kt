package com.settowally.settowally.presentation.photo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.common.NetworkResponseHandler
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
            when (val response = getSinglePhotoUseCase.invoke(photoId)) {
                is NetworkResponseHandler.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            photo = response.data,
                            selectedQualityLink = when (preferences.readQualitySettings()) {
                                "landscape" -> response.data?.src?.landscape
                                "large" -> response.data?.src?.large
                                "large2x" -> response.data?.src?.large2x
                                "medium" -> response.data?.src?.medium
                                "original" -> response.data?.src?.original
                                "portrait" -> response.data?.src?.portrait
                                "small" -> response.data?.src?.small
                                "tiny" -> response.data?.src?.tiny
                                else -> response.data?.src?.medium
                            }
                        )
                    }
                }

                is NetworkResponseHandler.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            photo = null,
                            error = response.message
                        )
                    }
                }

                else -> _uiState.update { it.copy(isLoading = true, error = null, photo = null) }
            }
        }
    }

}