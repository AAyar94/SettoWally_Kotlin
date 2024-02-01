package com.settowally.settowally.domain.usecase

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.SinglePhotoDto
import com.settowally.settowally.data.repository.RemoteDataDataRepositoryImpl
import javax.inject.Inject

class GetSinglePhotoUseCase @Inject constructor(
    private val remoteDataDataRepository: RemoteDataDataRepositoryImpl
) {

    suspend operator fun invoke(photoId: Int): NetworkResponseHandler<SinglePhotoDto> {
        return remoteDataDataRepository.getSinglePhotoFromRemote(photoId)
    }
}