package com.settowally.settowally.presentation.home

import com.settowally.settowally.domain.model.GridPhotoDataModel

data class HomeUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val photosList: List<GridPhotoDataModel>? = null,
    val searchQuery: String? = null,
    val searchPlaceHolder: String? = null,
    val isSearchFocused: Boolean = false
)