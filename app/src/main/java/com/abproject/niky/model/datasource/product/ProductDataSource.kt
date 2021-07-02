package com.abproject.niky.model.datasource.product

import com.abproject.niky.model.dataclass.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    fun getProductsBySort(
        sort: Int,
    ): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addProductToFavorite(
        product: Product,
    ): Completable

    fun deleteProductFromFavorite(
        product: Product,
    ): Completable
}