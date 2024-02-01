package com.settowally.settowally.domain.repository

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.core.util.Constant.PER_PAGE_PHOTO_COUNTER

import com.settowally.settowally.data.model.SinglePhotoDto
import com.settowally.settowally.data.model.PhotosDataModelDto

interface RemoteDataRepository {

    suspend fun getPhotosFromRemote(
        page: Int,
        pageSize: Int = PER_PAGE_PHOTO_COUNTER
    ): NetworkResponseHandler<PhotosDataModelDto>

    suspend fun getSearchedPhotosFromRemote(
        query: String,
        page: Int,
        pageSize: Int=PER_PAGE_PHOTO_COUNTER
    ):NetworkResponseHandler<PhotosDataModelDto>

    suspend fun getSinglePhotoFromRemote(
        photoId:Int
    ):NetworkResponseHandler<SinglePhotoDto>

}