package com.abproject.niky.view.favoriteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abproject.niky.R
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.EmptyState
import com.abproject.niky.model.dataclass.Product
import com.abproject.niky.model.repository.product.ProductRepository
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : NikyViewModel() {

    private val _getAllFavoriteProducts = MutableLiveData<List<Product>>()

    val getAllFavoriteProducts: LiveData<List<Product>> get() = _getAllFavoriteProducts

    fun getAllProductsFromFavorites() {
        productRepository.getFavoriteProducts()
            .asyncNetworkRequest()
            .subscribe(object : NikySingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(products: List<Product>) {
                    if (products.isNullOrEmpty()) {
                        _emptyStateStatusLiveData.value = EmptyState(
                            true,
                            R.string.nothingForShowInFavorites)
                    } else {
                        _emptyStateStatusLiveData.value = EmptyState(false)
                        _getAllFavoriteProducts.value = products
                    }
                }
            })
    }

    fun deleteAllProductsFromFavorite(): Completable {
        return productRepository.deleteAllProductsFromFavorites()
            .asyncNetworkRequest()
            .doOnComplete {
                _emptyStateStatusLiveData.postValue(EmptyState(
                    true,
                    R.string.nothingForShowInFavorites))
            }
    }

    fun deleteProductFromFavorites(
        product: Product,
    ): Completable {
        return productRepository.deleteProductFromFavorite(product)
            .asyncNetworkRequest()
            .doFinally {
                getAllProductsFromFavorites()
            }
    }
}