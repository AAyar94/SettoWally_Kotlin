package com.settowally.settowally.data.repository

import com.settowally.settowally.data.local.LocalDataSource
import com.settowally.settowally.data.model.SinglePhotoDto
import com.settowally.settowally.domain.repository.LocalDataRepository
import javax.inject.Inject

class LocalDataRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : LocalDataRepository {
    override suspend fun savePhotoToDatabase(singlePhotoDto: SinglePhotoDto) {
        return localDataSource.savePhotoToDb(singlePhotoDto)
    }

    override suspend fun getAllPhotosFromLocal(): List<SinglePhotoDto>? {
        return localDataSource.getAllSavedPhotos()
    }

    override suspend fun deletePhotoFromLocal(singlePhotoDto: SinglePhotoDto) {
        return localDataSource.deletePhotoFromDb(singlePhotoDto)
    }

    override suspend fun deleteAllPhotosLocal() {
        return localDataSource.deleteAllFavoritePhotos()
    }
}