package com.settowally.settowally.domain.repository

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.PhotosDataModelDto
import com.settowally.settowally.data.model.SinglePhotoDto

interface RemoteDataRepository {

    suspend fun getPhotosFromRemote(
        page: Int,
    ): NetworkResponseHandler<PhotosDataModelDto>

    suspend fun getSearchedPhotosFromRemote(
        query: String,
        page: Int,
    ): NetworkResponseHandler<PhotosDataModelDto>

    suspend fun getSinglePhotoFromRemote(
        photoId: Int
    ): NetworkResponseHandler<SinglePhotoDto>

}