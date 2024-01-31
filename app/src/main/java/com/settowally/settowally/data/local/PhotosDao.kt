package com.settowally.settowally.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.settowally.settowally.core.util.Constant.FAVORITE_PHOTOS_TABLE_NAME
import com.settowally.settowally.data.model.SinglePhotoDto

@Dao
interface PhotosDao {

    @Query("SELECT * FROM $FAVORITE_PHOTOS_TABLE_NAME")
    suspend fun getFavoritePhotos(): List<SinglePhotoDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPhoto(singlePhotoDto: SinglePhotoDto)

    @Delete
    suspend fun deletePhoto(singlePhotoDto: SinglePhotoDto)

    @Query("DELETE FROM $FAVORITE_PHOTOS_TABLE_NAME")
    suspend fun deleteAllFavoritePhotos()
}