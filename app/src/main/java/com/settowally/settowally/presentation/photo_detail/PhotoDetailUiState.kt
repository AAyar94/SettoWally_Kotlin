package com.settowally.settowally.presentation.photo_detail

import com.settowally.settowally.domain.model.SinglePhotoModel

data class PhotoDetailUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val photo: SinglePhotoModel? = null,
    val selectedQualityLink:String?=null
)