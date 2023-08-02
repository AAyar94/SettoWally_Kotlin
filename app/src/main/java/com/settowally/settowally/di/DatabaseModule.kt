package com.settowally.settowally.di

import android.content.Context
import androidx.room.Room
import com.settowally.settowally.common.Constant.Companion.DATABASE_NAME
import com.settowally.settowally.data.local.PhotosDao
import com.settowally.settowally.data.local.SavedPhotosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideSavedPhotosDatabase(@ApplicationContext context: Context): SavedPhotosDatabase {
        return Room.databaseBuilder(
            context,
            SavedPhotosDatabase::class.java,
            DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun providePhotoDat(database: SavedPhotosDatabase): PhotosDao {
        return database.photosDao()
    }

}