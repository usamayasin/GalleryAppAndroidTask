package com.example.avrioctask.di.modules

import com.example.avrioctask.data.repository.MediaRepository
import com.example.avrioctask.data.repository.MediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.ContentResolver

/**
 * The Dagger Module for providing repository instances.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        contentResolver: ContentResolver,
    ): MediaRepository {
        return MediaRepositoryImpl(contentResolver)
    }
}
