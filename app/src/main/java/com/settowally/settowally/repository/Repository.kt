package com.settowally.settowally.repository

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.repository.model.PhotosDataModel
import com.settowally.settowally.repository.remote.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getPhotosFromRemote(page: Int, perPage: Int): NetworkResponseHandler<PhotosDataModel> {
        return remoteDataSource.getPhotos(page = page.toString(), perPage = perPage.toString())
    }

}