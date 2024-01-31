package com.settowally.settowally.data.repository

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.PhotosDataModelDto
import com.settowally.settowally.data.model.SinglePhotoDto
import com.settowally.settowally.data.remote.RemoteDataSource
import com.settowally.settowally.domain.repository.RemoteDataRepository
import javax.inject.Inject

class RemoteDataDataRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RemoteDataRepository {
    override suspend fun getPhotosFromRemote(
        page: Int,
        pageSize: Int
    ): NetworkResponseHandler<PhotosDataModelDto> {
        return remoteDataSource.getPhotos(page, pageSize)
    }

    override suspend fun getSearchedPhotosFromRemote(
        query: String,
        page: Int,
        pageSize: Int
    ): NetworkResponseHandler<PhotosDataModelDto> {
        return remoteDataSource.getSearchedPhotos(query, page = page, pageSize = pageSize)
    }

    override suspend fun getSinglePhotoFromRemote(photoId: Int): NetworkResponseHandler<SinglePhotoDto> {
        return remoteDataSource.getPhoto(photoId)
    }
}