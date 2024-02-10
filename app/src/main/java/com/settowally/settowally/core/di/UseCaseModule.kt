package com.settowally.settowally.core.di

import com.settowally.settowally.data.repository.LocalDataRepositoryImpl
import com.settowally.settowally.data.repository.RemoteDataDataRepositoryImpl
import com.settowally.settowally.domain.repository.LocalDataRepository
import com.settowally.settowally.domain.usecase.DeleteAllFavoritePhotosUseCase
import com.settowally.settowally.domain.usecase.GetAllPhotosUseCase
import com.settowally.settowally.domain.usecase.GetFavoritePhotosUseCase
import com.settowally.settowally.domain.usecase.GetSinglePhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetFavoritePhotosUseCase(
        repository: LocalDataRepositoryImpl
    ): GetFavoritePhotosUseCase {
        return GetFavoritePhotosUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSinglePhotoUseCase(
        repository: RemoteDataDataRepositoryImpl
    ): GetSinglePhotoUseCase {
        return GetSinglePhotoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllPhotosUseCase(
        repository: RemoteDataDataRepositoryImpl
    ): GetAllPhotosUseCase {
        return GetAllPhotosUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllFavoritePhotosUseCase(
        repository: LocalDataRepositoryImpl
    ): DeleteAllFavoritePhotosUseCase {
        return DeleteAllFavoritePhotosUseCase(repository)
    }
}