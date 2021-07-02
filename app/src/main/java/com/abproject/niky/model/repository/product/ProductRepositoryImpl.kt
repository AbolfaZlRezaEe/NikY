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

    override fun getProductsBySort(sort: Int): Single<List<Product>> {
        return productRemoteDataSource.getProductsBySort(sort)
    }

    override fun getBanner(): Single<List<Banner>> {
        return bannerRemoteDataSource.getBanners()
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