package com.settowally.settowally.presentation.favorites

import com.settowally.settowally.data.model.SinglePhotoDto
import com.settowally.settowally.domain.model.GridPhotoDataModel

data class FavoriteScreenUIState(
    val isLoading: Boolean = true,
    var list: List<GridPhotoDataModel>? = emptyList()
)