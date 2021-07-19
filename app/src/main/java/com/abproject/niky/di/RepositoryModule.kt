package com.abproject.niky.di

import android.content.SharedPreferences
import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.datasource.banner.BannerRemoteDataSource
import com.abproject.niky.model.datasource.cart.CartRemoteDataSource
import com.abproject.niky.model.datasource.comment.CommentRemoteDataSource
import com.abproject.niky.model.datasource.product.ProductLocalDataSource
import com.abproject.niky.model.datasource.product.ProductRemoteDataSource
import com.abproject.niky.model.datasource.shipping.ShippingRemoteDataSource
import com.abproject.niky.model.datasource.token.TokenLocalDataSource
import com.abproject.niky.model.datasource.token.TokenRemoteDataSource
import com.abproject.niky.model.repository.cart.CartRepository
import com.abproject.niky.model.repository.cart.CartRepositoryImpl
import com.abproject.niky.model.repository.comment.CommentRepository
import com.abproject.niky.model.repository.comment.CommentRepositoryImpl
import com.abproject.niky.model.repository.product.ProductRepository
import com.abproject.niky.model.repository.product.ProductRepositoryImpl
import com.abproject.niky.model.repository.shipping.ShippingRepository
import com.abproject.niky.model.repository.shipping.ShippingRepositoryImpl
import com.abproject.niky.model.repository.user.UserRepository
import com.abproject.niky.model.repository.user.UserRepositoryImpl
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

    @Singleton
    @Provides
    fun provideCartRepository(
        apiService: NikyApiService,
    ): CartRepository {
        return CartRepositoryImpl(
            CartRemoteDataSource(apiService)
        )
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: NikyApiService,
        sharedPreferences: SharedPreferences,
    ): UserRepository {
        return UserRepositoryImpl(
            TokenRemoteDataSource(apiService),
            TokenLocalDataSource(sharedPreferences)
        )
    }

    @Singleton
    @Provides
    fun provideShippingRepository(
        apiService: NikyApiService,
    ): ShippingRepository {
        return ShippingRepositoryImpl(
            ShippingRemoteDataSource(apiService)
        )
    }
}