package com.settowally.settowally.domain.usecase

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.domain.model.HomePhotoDataModel
import com.settowally.settowally.domain.repository.RemoteDataRepository
import javax.inject.Inject

class GetAllPhotosUseCase @Inject constructor(
    private val remoteDataRepository: RemoteDataRepository
) {

    suspend operator fun invoke(page: Int): NetworkResponseHandler<List<HomePhotoDataModel>> {
        val response = remoteDataRepository.getPhotosFromRemote(page)
        return try {
            val returnModel = response.data?.photos!!.map { dto ->
                HomePhotoDataModel(
                    id = dto.id,
                    alt = dto.alt,
                    photographer = dto.photographer,
                    imgLink = dto.src.medium
                )
            }
            NetworkResponseHandler.Success(returnModel)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponseHandler.Error(e.message)
        }
    }
}