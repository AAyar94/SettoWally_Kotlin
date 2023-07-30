package com.settowally.settowally.repository.remote

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.repository.remote.model.PhotosDataModel
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

}