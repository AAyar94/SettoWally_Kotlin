package com.settowally.settowally.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.settowally.settowally.common.Constant.Companion.FAVORITE_PHOTOS_TABLE_NAME
import com.settowally.settowally.data.model.Photo

@Dao
interface PhotosDao {

    @Query("SELECT * FROM $FAVORITE_PHOTOS_TABLE_NAME")
    suspend fun getFavoritePhotos(): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPhoto(photo: Photo)

    @Delete
    suspend fun deletePhoto(photo: Photo)

    @Query("DELETE FROM $FAVORITE_PHOTOS_TABLE_NAME")
    suspend fun deleteAllFavoritePhotos()
}