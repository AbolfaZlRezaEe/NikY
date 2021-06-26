package com.abproject.niky.di

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.datasource.banner.BannerRemoteDataSource
import com.abproject.niky.model.datasource.comment.CommentRemoteDataSource
import com.abproject.niky.model.datasource.product.ProductLocalDataSource
import com.abproject.niky.model.datasource.product.ProductRemoteDataSource
import com.abproject.niky.model.repository.comment.CommentRepository
import com.abproject.niky.model.repository.comment.CommentRepositoryImpl
import com.abproject.niky.model.repository.product.ProductRepository
import com.abproject.niky.model.repository.product.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository module provide Repositories that use in viewModels.
 * this functions takes dependencies like apiService or Dao
 * and pass them into RepositoryImpls constructor.
 * Note: this Module functions Should be annotate with Singleton!
 */
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
            ProductLocalDataSource(),
            BannerRemoteDataSource(apiService)
        )
    }

    @Singleton
    @Provides
    fun provideCommentRepository(
        apiService: NikyApiService,
    ): CommentRepository {
        return CommentRepositoryImpl(
            CommentRemoteDataSource(apiService)
        )
    }
}