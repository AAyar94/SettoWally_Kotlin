package com.settowally.settowally.domain.usecase

import com.settowally.settowally.common.NetworkResponseHandler
import com.settowally.settowally.data.model.Src
import com.settowally.settowally.data.repository.RemoteDataDataRepositoryImpl
import com.settowally.settowally.domain.model.SinglePhotoModel
import javax.inject.Inject

class GetSinglePhotoUseCase @Inject constructor(
    private val remoteDataDataRepository: RemoteDataDataRepositoryImpl
) {

    suspend operator fun invoke(photoId: Int): NetworkResponseHandler.Success<SinglePhotoModel?> {
        val response = remoteDataDataRepository.getSinglePhotoFromRemote(photoId)
        val responseModel = response.data.let {
            it?.let { it1 ->
                SinglePhotoModel(
                    id = it1.id,
                    avgColor = it.avgColor,
                    height = it.height,
                    width = it.width,
                    photographer = it.photographer,
                    linkSource = Src(
                        landscape = it.src.landscape,
                        large = it.src.large,
                        large2x = it.src.large2x,
                        medium = it.src.medium,
                        original = it.src.original,
                        portrait = it.src.portrait,
                        small = it.src.small,
                        tiny = it.src.tiny
                    )
                )
            }
        }
        return NetworkResponseHandler.Success(responseModel)
    }
}