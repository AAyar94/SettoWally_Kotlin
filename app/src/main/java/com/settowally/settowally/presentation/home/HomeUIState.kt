package com.settowally.settowally.presentation.home

import com.settowally.settowally.domain.model.HomePhotoDataModel

data class HomeUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val photosList: List<HomePhotoDataModel>? = null,
    val searchQuery: String? = null,
    val searchPlaceHolder: String? = null,
    val isSearchFocused: Boolean = false
)