package com.settowally.settowally.data.remote

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotosDataModel
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val photosAPI: PhotosAPI,
) {

    suspend fun getPhotos(page: String, perPage: String): NetworkResponseHandler<PhotosDataModel> {
        val response = try {
            photosAPI.getPhotosPage(page, perPage)
        } catch (e: Exception) {
            return NetworkResponseHandler.Error(e.message)
        }
        return NetworkResponseHandler.Success(response)
    }

    suspend fun getSearchedPhotos(
        query: String,
        page: String,
        perPage: String,
    ): NetworkResponseHandler<PhotosDataModel> {
        val response = try {
            photosAPI.getSearchedPhotos(query, page, perPage)
        } catch (e: Exception) {
            return NetworkResponseHandler.Error(e.message)
        }
        return NetworkResponseHandler.Success(response)
    }

    suspend fun getPhoto(id: Int): NetworkResponseHandler<Photo> {
        val photoResponse = try {
            photosAPI.getPhoto(id)
        } catch (e: Exception) {
            return NetworkResponseHandler.Error(e.message)
        }
        return NetworkResponseHandler.Success(photoResponse)
    }

}