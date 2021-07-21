package com.abproject.niky.model.repository.product

import com.abproject.niky.model.dataclass.Banner
import com.abproject.niky.model.dataclass.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProductsBySort(
        sort: Int,
    ): Single<List<Product>>

    fun searchInProductsWithProductTitle(
        productTitle: String,
    ): Single<List<Product>>

    fun getBanner(): Single<List<Banner>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addProductToFavorite(
        product: Product,
    ): Completable

    fun deleteProductFromFavorite(
        product: Product,
    ): Completable

    fun deleteAllProductsFromFavorites(): Completable

}