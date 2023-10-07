package com.settowally.settowally.data.repository

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.local.LocalDataSource
import com.settowally.settowally.data.model.Photo
import com.settowally.settowally.data.model.PhotosDataModel
import com.settowally.settowally.data.remote.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {


    /**     REMOTE      */
    suspend fun getPhotosFromRemote(
        page: Int,
        perPage: Int,
    ): NetworkResponseHandler<PhotosDataModel> {
        return remoteDataSource.getPhotos(page = page.toString(), perPage = perPage.toString())
    }

    suspend fun getSearchedPhotosFromRemote(
        query: String,
        page: Int,
        perPage: Int,
    ): NetworkResponseHandler<PhotosDataModel> {
        return remoteDataSource.getSearchedPhotos(query, page.toString(), perPage.toString())
    }

    suspend fun getPhotoFromRemote(photoId: Int): NetworkResponseHandler<Photo> {
        return remoteDataSource.getPhoto(photoId)
    }


    /**     LOCAL       */
    suspend fun savePhotoToDb(photo: Photo) {
        return localDataSource.savePhotoToDb(photo)
    }

    suspend fun getAllPhotosFromDb(): List<Photo> {
        return localDataSource.getAllSavedPhotos()
    }

    suspend fun deletePhotoFromDb(photo: Photo){
        return localDataSource.deletePhotoFromDb(photo)
    }

    suspend fun deleteAllFavoritePhotos(){
        return localDataSource.deleteAllFavoritePhotos()
    }

}