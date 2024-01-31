package com.settowally.settowally.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.settowally.settowally.core.util.Constant.PHOTOS_SRC_TABLE_NAME

@Entity(tableName = PHOTOS_SRC_TABLE_NAME)

data class Src(
    val landscape: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val original: String,
    val portrait: String,
    val small: String,
    val tiny: String
) {
    @PrimaryKey()
    var id = small.toString()
}