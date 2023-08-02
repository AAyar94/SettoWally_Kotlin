package com.settowally.settowally.data.local.coverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.settowally.settowally.data.local.entity.SavedPhoto

class PhotoTypeConverter {

    @TypeConverter
    fun fromJson(json: String): List<SavedPhoto>? {
        return Gson().fromJson(json, object : TypeToken<List<SavedPhoto>>() {}.type)
    }

    @TypeConverter
    fun toJson(media: List<SavedPhoto>?): String {
        return Gson().toJson(media)
    }


}