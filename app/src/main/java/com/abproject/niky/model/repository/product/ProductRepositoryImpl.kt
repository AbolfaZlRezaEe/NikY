package com.abproject.niky.model.repository.product

import com.abproject.niky.model.datasource.product.ProductDataSource
import com.abproject.niky.model.model.Product
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductDataSource,
    private val productLocalDataSource: ProductDataSource,
) : ProductRepository {

    override fun getProducts(): Single<List<Product>> {
        return productRemoteDataSource.getProducts()
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addProductToFavorite(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteProductFromFavorite(product: Product): Completable {
        TODO("Not yet implemented")
    }
}