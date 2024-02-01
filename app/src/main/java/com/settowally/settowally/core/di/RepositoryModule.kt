package com.settowally.settowally.core.di

import com.settowally.settowally.data.local.LocalDataSource
import com.settowally.settowally.data.remote.RemoteDataSource
import com.settowally.settowally.data.repository.LocalDataRepositoryImpl
import com.settowally.settowally.data.repository.RemoteDataDataRepositoryImpl
import com.settowally.settowally.domain.repository.RemoteDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalRepository(
        localDataSource: LocalDataSource
    ): LocalDataRepositoryImpl {
        return LocalDataRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(
        remoteDataSource: RemoteDataSource
    ): RemoteDataRepository {
        return RemoteDataDataRepositoryImpl(remoteDataSource)
    }

}