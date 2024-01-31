package com.settowally.settowally.data.remote

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.PhotosDataModelDto
import com.settowally.settowally.data.model.SinglePhotoDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val photosAPI: PhotosAPI,
) {

    suspend fun getPhotos(page: Int, pageSize: Int): NetworkResponseHandler<PhotosDataModelDto> {
        val response = try {
            photosAPI.getPhotosPage(page, pageSize)
        } catch (e: Exception) {
            return NetworkResponseHandler.Error(e.message)
        }
        return NetworkResponseHandler.Success(response)
    }

    suspend fun getSearchedPhotos(
        query: String,
        page: Int,
        pageSize: Int,
    ): NetworkResponseHandler<PhotosDataModelDto> {
        val response = try {
            photosAPI.getSearchedPhotos(query = query, page = page, perPage = pageSize)
        } catch (e: Exception) {
            return NetworkResponseHandler.Error(e.message)
        }
        return NetworkResponseHandler.Success(response)
    }

    suspend fun getPhoto(id: Int): NetworkResponseHandler<SinglePhotoDto> {
        val photoResponse = try {
            photosAPI.getPhoto(id)
        } catch (e: Exception) {
            return NetworkResponseHandler.Error(e.message)
        }
        return NetworkResponseHandler.Success(photoResponse)
    }

}