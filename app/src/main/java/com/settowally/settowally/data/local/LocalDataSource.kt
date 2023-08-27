package com.settowally.settowally.data.local

import com.settowally.settowally.data.model.Photo
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: PhotosDao,
) {

    suspend fun getAllSavedPhotos(): List<Photo> {
        return dao.getFavoritePhotos()
    }

    suspend fun savePhotoToDb(photo: Photo){
        return dao.insertNewPhoto(photo)
    }

    suspend fun deletePhotoFromDb(photo: Photo){
        return dao.deletePhoto(photo)
    }

}