package com.settowally.settowally.data.local

import com.settowally.settowally.data.model.SinglePhotoDto
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: PhotosDao,
) {

    suspend fun getAllSavedPhotos(): List<SinglePhotoDto> {
        return dao.getFavoritePhotos()
    }

    suspend fun savePhotoToDb(singlePhotoDto: SinglePhotoDto) {
        return dao.insertNewPhoto(singlePhotoDto)
    }

    suspend fun deletePhotoFromDb(singlePhotoDto: SinglePhotoDto) {
        return dao.deletePhoto(singlePhotoDto)
    }

    suspend fun deleteAllFavoritePhotos() {
        return dao.deleteAllFavoritePhotos()
    }
}