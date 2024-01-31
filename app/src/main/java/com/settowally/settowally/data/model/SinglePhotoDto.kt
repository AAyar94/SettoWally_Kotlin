package com.settowally.settowally.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.settowally.settowally.core.util.Constant

@Entity(tableName = Constant.FAVORITE_PHOTOS_TABLE_NAME)
data class SinglePhotoDto(
    val alt: String,
    @SerializedName("avg_color")
    val avgColor: String,
    val height: Int,
    @PrimaryKey
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    @SerializedName("photographer_id")
    val photographerId: Int,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    val src: Src,
    val url: String,
    val width: Int
)