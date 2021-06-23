package com.abproject.niky.model.repository.product

import com.abproject.niky.model.model.Banner
import com.abproject.niky.model.model.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProductsBySort(
        sort: Int,
    ): Single<List<Product>>

    fun getBanner(): Single<List<Banner>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addProductToFavorite(
        product: Product,
    ): Completable

    fun deleteProductFromFavorite(
        product: Product,
    ): Completable
}