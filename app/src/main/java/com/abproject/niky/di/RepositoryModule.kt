package com.abproject.niky.di

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.datasource.product.ProductLocalDataSource
import com.abproject.niky.model.datasource.product.ProductRemoteDataSource
import com.abproject.niky.model.repository.product.ProductRepository
import com.abproject.niky.model.repository.product.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        apiService: NikyApiService,
    ): ProductRepository {
        return ProductRepositoryImpl(
            ProductRemoteDataSource(apiService),
            ProductLocalDataSource()
        )
    }
}