package com.abproject.niky.model.datasource.product

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.dataclass.Product
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiService: NikyApiService,
) : ProductDataSource {

    override fun getProductsBySort(
        sort: Int,
    ): Single<List<Product>> {
        return apiService.getProducts(sort.toString())
    }

    override fun searchInProducts(
        productTitle: String
    ): Single<List<Product>> {
        return apiService.searchInProducts(productTitle)
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
        throw IllegalStateException("ProductRemoteDataSource -> this function not available in this class!")
    }

    override fun addProductToFavorite(product: Product): Completable {
        throw IllegalStateException("ProductRemoteDataSource -> this function not available in this class!")
    }

    override fun deleteProductFromFavorite(product: Product): Completable {
        throw IllegalStateException("ProductRemoteDataSource -> this function not available in this class!")
    }

    override fun deleteAllProductsFromFavorites(): Completable {
        throw IllegalStateException("ProductRemoteDataSource -> this function not available in this class!")
    }
}