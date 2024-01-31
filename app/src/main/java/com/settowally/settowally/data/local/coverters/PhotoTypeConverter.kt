package com.settowally.settowally.data.local.coverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.settowally.settowally.data.model.SinglePhotoDto

class PhotoTypeConverter {

    @TypeConverter
    fun fromJson(json: String): List<SinglePhotoDto>? {
        return Gson().fromJson(json, object : TypeToken<List<SinglePhotoDto>>() {}.type)
    }

    @TypeConverter
    fun toJson(media: List<SinglePhotoDto>?): String {
        return Gson().toJson(media)
    }


}