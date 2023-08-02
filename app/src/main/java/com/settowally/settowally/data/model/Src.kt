package com.settowally.settowally.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.settowally.settowally.common.Constant.Companion.PHOTOS_SRC_TABLE_NAME
import kotlinx.parcelize.Parcelize
@Entity(tableName = PHOTOS_SRC_TABLE_NAME)
@Parcelize
data class Src(
    val landscape: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val original: String,
    val portrait: String,
    val small: String,
    val tiny: String
) : Parcelable{
    @PrimaryKey()
    var id = small.toString()
}