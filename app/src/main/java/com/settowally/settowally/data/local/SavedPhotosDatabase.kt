package com.settowally.settowally.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.settowally.settowally.data.local.coverters.PhotoTypeConverter
import com.settowally.settowally.data.local.coverters.SrcTypeConverter
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.Src


@Database(
    entities = [Photo::class, Src::class],
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