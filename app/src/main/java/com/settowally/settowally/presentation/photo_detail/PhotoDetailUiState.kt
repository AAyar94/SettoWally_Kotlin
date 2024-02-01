package com.settowally.settowally.presentation.photo_detail

import com.settowally.settowally.data.model.SinglePhotoDto

data class PhotoDetailUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val photo: SinglePhotoDto? = null,
    val selectedQualityLink:String?=null
)