package com.settowally.settowally.data.model

import com.google.gson.annotations.SerializedName


data class PhotosDataModelDto(
    @SerializedName("next_page")
    val nextPage: String?,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val singlePhotoDtos: List<SinglePhotoDto>,
    @SerializedName("prev_page")
    val prevPage: String?,
    @SerializedName("total_results")
    val totalResults: Int?
)