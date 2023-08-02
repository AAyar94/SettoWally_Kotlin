package com.settowally.settowally.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.settowally.settowally.data.local.coverters.PhotoTypeConverter
import com.settowally.settowally.data.local.coverters.SrcTypeConverter
import com.settowally.settowally.data.local.entity.SavedPhoto
import com.settowally.settowally.data.remote.model.Src


@Database(
    entities = [SavedPhoto::class, Src::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    PhotoTypeConverter::class,
    SrcTypeConverter::class
)
abstract class SavedPhotosDatabase  : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}