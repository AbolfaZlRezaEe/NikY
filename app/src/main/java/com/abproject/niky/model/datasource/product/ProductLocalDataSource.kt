package com.abproject.niky.model.datasource.product

import com.abproject.niky.model.database.dao.ProductDao
import com.abproject.niky.model.dataclass.Product
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val productDao: ProductDao,
) : ProductDataSource {

    override fun getProductsBySort(sort: Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun searchInProducts(productTitle: String): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
        return productDao.getAllFavoritesProduct()
    }

    override fun addProductToFavorite(
        product: Product,
    ): Completable {
        return productDao.addProductToFavorites(product)
    }

    override fun deleteProductFromFavorite(
        product: Product,
    ): Completable {
        return productDao.deleteProductFromFavorites(product)
    }

    override fun deleteAllProductsFromFavorites(): Completable {
        return productDao.deleteAllProductsFromFavorites()
    }
}