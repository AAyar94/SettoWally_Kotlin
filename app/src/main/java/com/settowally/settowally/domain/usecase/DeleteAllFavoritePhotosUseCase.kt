package com.settowally.settowally.domain.usecase

import com.settowally.settowally.domain.repository.LocalDataRepository
import javax.inject.Inject

class DeleteAllFavoritePhotosUseCase @Inject constructor(
    private val localDataRepository: LocalDataRepository
) {

    suspend operator fun invoke() {
        return localDataRepository.deleteAllPhotosLocal()
    }

}