package com.settowally.settowally.domain.repository

import com.settowally.settowally.data.model.SinglePhotoDto

interface LocalDataRepository {

    suspend fun savePhotoToDatabase(singlePhotoDto: SinglePhotoDto)

    suspend fun getAllPhotosFromLocal(): List<SinglePhotoDto>?

    suspend fun deletePhotoFromLocal(singlePhotoDto: SinglePhotoDto)

    suspend fun deleteAllPhotosLocal()

}