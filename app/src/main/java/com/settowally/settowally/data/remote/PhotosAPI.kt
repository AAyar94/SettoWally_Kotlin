package com.settowally.settowally.data.remote

import com.settowally.settowally.core.util.Constant.API_KEY
import com.settowally.settowally.data.model.PhotosDataModelDto
import com.settowally.settowally.data.model.SinglePhotoDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotosAPI {

    @Headers(
        "Accept: application/json",
        "Authorization: $API_KEY"
    )
    @GET("curated/")
    suspend fun getPhotosPage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): PhotosDataModelDto

    @Headers(
        "Accept: application/json",
        "Authorization: $API_KEY"
    )
    @GET("search/")
    suspend fun getSearchedPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): PhotosDataModelDto

    @Headers(
        "Accept: application/json",
        "Authorization: $API_KEY"
    )
    @GET("photos/")
    suspend fun getPhoto(
        @Query("id") photoId: Int,
    ): SinglePhotoDto


}