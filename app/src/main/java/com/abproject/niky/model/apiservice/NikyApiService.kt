package com.abproject.niky.model.apiservice

import com.abproject.niky.model.model.Banner
import com.abproject.niky.model.model.Product
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * this Interface is responsible for sending all requests to the server.
 * also NikyApiService work with Rxjava library for receive data from
 * server.
 */
interface NikyApiService {

    @GET("product/list")
    fun getProducts(
        @Query("sort") sort: String,
    ): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>
}