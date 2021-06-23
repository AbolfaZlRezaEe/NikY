package com.abproject.niky.di

import com.abproject.niky.components.imageview.ImageLoadingService
import com.abproject.niky.view.home.ProductHomeAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * this module use for provide Adapter dependencies
 * in all classes in application.
 * Note: this module functions Shouldn't be annotated with Singleton!
 */
@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {

    @Provides
    fun provideProductHomeAdapter(
        imageLoadingService: ImageLoadingService,
    ): ProductHomeAdapter {
        return ProductHomeAdapter(imageLoadingService)
    }
}