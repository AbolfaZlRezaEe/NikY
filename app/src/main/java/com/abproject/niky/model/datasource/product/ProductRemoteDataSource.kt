package com.abproject.niky.model.datasource.product

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.model.Product
import com.abproject.niky.utils.Variables.PRODUCT_SORT_LATEST
import com.abproject.niky.utils.Variables.PRODUCT_SORT_POPULAR
import com.abproject.niky.utils.Variables.PRODUCT_SORT_PRICE_ASC
import com.abproject.niky.utils.Variables.PRODUCT_SORT_PRICE_DESC
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiService: NikyApiService,
) : ProductDataSource {

    override fun getProductsBySort(sort: Int): Single<List<Product>> {
        return apiService.getProducts(sort.toString())
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