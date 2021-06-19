package com.abproject.niky.model.apiservice

import com.abproject.niky.model.model.Product
import io.reactivex.Single
import retrofit2.http.GET


interface NikyApiService {

    @GET("product/list")
    fun getProducts(): Single<List<Product>>
}