package com.settowally.settowally.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.domain.usecase.GetFavoritePhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritePhotosUseCase: GetFavoritePhotosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun getFavoritePhotos() {
        viewModelScope.launch {
            val response = getFavoritePhotosUseCase.invoke()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    list = response
                )
            }
        }
    }

}