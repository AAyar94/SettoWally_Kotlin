package com.settowally.settowally.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.settowally.settowally.common.Constant.Companion.FAVORITE_PHOTOS_TABLE_NAME
import com.settowally.settowally.data.remote.model.Src
import kotlinx.parcelize.Parcelize

@Entity(tableName = FAVORITE_PHOTOS_TABLE_NAME)
@Parcelize
data class SavedPhoto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val url: String,
    val photographer: String? = null,
    @SerializedName("photographer_url")
    val photographerUrl: String? = null,
    @SerializedName("avg_color")
    val avgColor: String? = "#FFFFFF",
    val src: Src? = null,
    var isLiked: Boolean,
    var isDownloaded: Boolean,
    val image: String? = null,
) : Parcelable