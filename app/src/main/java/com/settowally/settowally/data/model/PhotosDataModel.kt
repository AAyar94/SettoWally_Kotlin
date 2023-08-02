package com.settowally.settowally.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotosDataModel(
    @SerializedName("next_page")
    val nextPage: String?,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val photos: List<Photo>,
    @SerializedName("prev_page")
    val prevPage: String?,
    @SerializedName("total_results")
    val totalResults: Int?
) : Parcelable