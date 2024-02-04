package com.settowally.settowally.domain.usecase

import com.settowally.settowally.data.model.SinglePhotoDto
import com.settowally.settowally.data.repository.LocalDataRepositoryImpl
import com.settowally.settowally.domain.model.GridPhotoDataModel
import javax.inject.Inject

class GetFavoritePhotosUseCase @Inject constructor(
    private val repository: LocalDataRepositoryImpl
) {

    suspend operator fun invoke(): List<GridPhotoDataModel>? {
        val response = repository.getAllPhotosFromLocal()
        if (response != null) {
            return response.map {
                GridPhotoDataModel(
                    id = it.id,
                    alt = it.alt,
                    photographer = it.photographer,
                    imgLink = it.src.medium
                )
            }
        }
        return null
    }
}