package com.abproject.niky.model.repository.product

import com.abproject.niky.model.datasource.banner.BannerDataSource
import com.abproject.niky.model.datasource.product.ProductDataSource
import com.abproject.niky.model.dataclass.Banner
import com.abproject.niky.model.dataclass.Product
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductDataSource,
    private val productLocalDataSource: ProductDataSource,
    private val bannerRemoteDataSource: BannerDataSource,
) : ProductRepository {

    override fun getProductsBySort(
        sort: Int,
    ): Single<List<Product>> {
        return productLocalDataSource.getFavoriteProducts()
            .flatMap { favoritesProduct ->
                productRemoteDataSource.getProductsBySort(sort)
                    .doOnSuccess { products ->
                        val favoriteProductsId = favoritesProduct.map { product -> product.id }
                        products.forEach { product ->
                            if (favoriteProductsId.contains(product.id))
                                product.isFavorite = true
                        }
                    }
            }
    }

    override fun getBanner(): Single<List<Banner>> {
        return bannerRemoteDataSource.getBanners()
    }

    override fun getFavoriteProducts(): Single<List<Product>> {
        return productLocalDataSource.getFavoriteProducts()
    }

    override fun addProductToFavorite(
        product: Product,
    ): Completable {
        return productLocalDataSource.addProductToFavorite(product)
    }

    override fun deleteProductFromFavorite(
        product: Product,
    ): Completable {
        return productLocalDataSource.deleteProductFromFavorite(product)
    }

    override fun deleteAllProductsFromFavorites(): Completable {
        return productLocalDataSource.deleteAllProductsFromFavorites()
    }
}