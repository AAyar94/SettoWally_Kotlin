package com.settowally.settowally.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.core.util.UiEvent
import com.settowally.settowally.domain.usecase.GetAllPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getAllPhotosUseCase: GetAllPhotosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun searchQueryUpdate(string: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(searchQuery = string) }
        }
    }

    fun searchFocusChange(boolean: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSearchFocused = boolean
                )
            }
        }
    }

    fun onSearch(query: String) {

    }

    fun getPhotos(page: Int) {
        viewModelScope.launch {
            when (val response = getAllPhotosUseCase.invoke(page)) {
                is NetworkResponseHandler.Loading -> {
                    viewModelScope.launch {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                photosList = null,
                            )
                        }
                    }
                }

                is NetworkResponseHandler.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            photosList = response.data
                        )
                    }
                }

                is NetworkResponseHandler.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = response.message,
                            photosList = null
                        )
                    }
                }

                else -> Unit
            }
        }
    }

}