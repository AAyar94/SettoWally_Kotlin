package com.settowally.settowally.repository.remote

import com.settowally.settowally.common.Constant.Companion.API_KEY
import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.repository.model.PhotosDataModel
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
        @Query("page") page: String,
        @Query("per_page") perPage: String,
    ): PhotosDataModel
}