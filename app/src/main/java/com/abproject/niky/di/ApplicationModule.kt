package com.abproject.niky.di

import android.content.Context
import androidx.room.Room
import com.abproject.niky.components.imageview.FrescoImageViewService
import com.abproject.niky.components.imageview.ImageLoadingService
import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.database.NikyDatabase
import com.abproject.niky.utils.Variables.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Application Module provide an instance that use in classes in application
 * also this module can use in view.
 * For example: imageLoadingService used in ProductHomeAdapter class.
 * Note: this module functions should be annotate with Singleton!
 */
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideNikyApiServiceInstance(
        retrofit: Retrofit,
    ): NikyApiService {
        return retrofit.create(NikyApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNikyDatabase(
        @ApplicationContext context: Context,
    ): NikyDatabase {
        return Room.databaseBuilder(
            context,
            NikyDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideImageLoadingService(): ImageLoadingService {
        return FrescoImageViewService()
    }
}