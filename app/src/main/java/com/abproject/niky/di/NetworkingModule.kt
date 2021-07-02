package com.abproject.niky.di

import com.abproject.niky.model.dataclass.TokenContainer
import com.abproject.niky.utils.other.Variables
import com.abproject.niky.utils.other.Variables.HEADER_REQUEST_KEY_ACCEPT
import com.abproject.niky.utils.other.Variables.HEADER_REQUEST_KEY_AUTHORIZATION
import com.abproject.niky.utils.other.Variables.HEADER_REQUEST_VALUE_JSON
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * this module use for creating instance of Retrofit, HttpClient and
 * HttpLoggingInterceptor.
 * Note: this module functions should be annotate with Singleton!
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        httpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Variables.BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClientForRetrofit(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            //adding headers to all requests
            .addInterceptor { oldInterceptor ->

                val oldRequest = oldInterceptor.request()
                val newRequest = oldRequest.newBuilder()

                //pass access token if that not null
                if (!TokenContainer.accessToken.isNullOrEmpty())
                    newRequest.addHeader(
                        HEADER_REQUEST_KEY_AUTHORIZATION,
                        TokenContainer.accessToken!!)

                //set application/json header for all requests
                newRequest.addHeader(HEADER_REQUEST_KEY_ACCEPT, HEADER_REQUEST_VALUE_JSON)
                //set the old request method and body to the new request
                newRequest.method(oldRequest.method, oldRequest.body)
                return@addInterceptor oldInterceptor.proceed(newRequest.build())
            }.addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}