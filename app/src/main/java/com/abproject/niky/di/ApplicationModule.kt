package com.abproject.niky.di

import android.content.Context
import androidx.room.Room
import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.database.NikyDatabase
import com.abproject.niky.utils.Variables.BASE_URL
import com.abproject.niky.utils.Variables.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

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
}