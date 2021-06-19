package com.abproject.niky.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.model.Product
import com.abproject.niky.model.repository.product.ProductRepository
import com.abproject.niky.utils.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : NikyViewModel() {

    private val _getProducts = MutableLiveData<List<Product>>()

    private val getProducts: LiveData<List<Product>> get() = _getProducts


    fun getAllProducts() {
        productRepository.getProducts()
            .asyncNetworkRequest()
            .subscribe(object : NikySingleObserver<List<Product>>(compositeDisposable) {

                override fun onSuccess(t: List<Product>) {
                    Timber.d(t.toString())
                }

            })
    }

}