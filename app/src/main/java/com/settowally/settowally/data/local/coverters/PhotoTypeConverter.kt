package com.settowally.settowally.data.local.coverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.settowally.settowally.data.model.Photo

class PhotoTypeConverter {

    @TypeConverter
    fun fromJson(json: String): List<Photo>? {
        return Gson().fromJson(json, object : TypeToken<List<Photo>>() {}.type)
    }

    @TypeConverter
    fun toJson(media: List<Photo>?): String {
        return Gson().toJson(media)
    }


}