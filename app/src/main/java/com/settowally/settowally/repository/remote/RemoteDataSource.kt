package com.settowally.settowally.repository.remote

import com.settowally.settowally.repository.model.PhotosDataModel
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val photosAPI: PhotosAPI,
) {

    suspend fun getPhotos(page: String, perPage: String): PhotosDataModel {
        return photosAPI.getPhotosPage(page = page, perPage = perPage)
    }

}