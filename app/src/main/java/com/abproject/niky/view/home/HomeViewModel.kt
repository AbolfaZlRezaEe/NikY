package com.abproject.niky.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.Banner
import com.abproject.niky.model.dataclass.Product
import com.abproject.niky.model.repository.product.ProductRepository
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_LATEST
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_POPULAR
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_PRICE_ASC
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_PRICE_DESC
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : NikyViewModel() {

    private val _getLatestProducts = MutableLiveData<List<Product>>()
    private val _getPopularProducts = MutableLiveData<List<Product>>()
    private val _getPriceDescProducts = MutableLiveData<List<Product>>()
    private val _getPriceAscProducts = MutableLiveData<List<Product>>()
    private val _getBanners = MutableLiveData<List<Banner>>()

    val getLatestProducts: LiveData<List<Product>> get() = _getLatestProducts
    val getPopularProducts: LiveData<List<Product>> get() = _getPopularProducts
    val getPriceDescProducts: LiveData<List<Product>> get() = _getPriceDescProducts
    val getPriceAscProducts: LiveData<List<Product>> get() = _getPriceAscProducts
    val getBanners: LiveData<List<Banner>> get() = _getBanners

    /**
     * this variable save viewPager height ans reloaded again when
     * fragment restarted.
     */
    var viewPagerHeight: Float = 0F


    /**
     * this function takes sort and live data for post value
     * and send request to the server. after success livedata post
     * receive data into view.
     */
    private fun getProductsBySort(
        sort: Int,
        liveData: MutableLiveData<List<Product>>,
    ) {
        if (processForGettingDataInInternetConnection(liveData)) {
            _progressbarStatus.postValue(true)
            productRepository.getProductsBySort(sort)
                .asyncNetworkRequest()
                .doFinally { _progressbarStatus.postValue(false) }
                .subscribe(object : NikySingleObserver<List<Product>>(compositeDisposable) {
                    override fun onSuccess(response: List<Product>) {
                        liveData.postValue(response)
                    }
                })
        }
    }

    // sending all requests.
    fun requestForReceiveProducts() {
        getProductsBySort(
            PRODUCT_SORT_POPULAR,
            _getPopularProducts
        )
        getProductsBySort(
            PRODUCT_SORT_LATEST,
            _getLatestProducts
        )
        getProductsBySort(
            PRODUCT_SORT_PRICE_ASC,
            _getPriceAscProducts
        )
        getProductsBySort(
            PRODUCT_SORT_PRICE_DESC,
            _getPriceDescProducts
        )
    }

    /**
     * sending request for receive banners and sho them into
     * view pager in view.
     */
    fun getAllBanners() {
        if (processForGettingDataInInternetConnection(_getBanners)) {
            _progressbarStatus.postValue(true)
            productRepository.getBanner()
                .asyncNetworkRequest()
                .doFinally { _progressbarStatus.postValue(false) }
                .subscribe(object : NikySingleObserver<List<Banner>>(compositeDisposable) {
                    override fun onSuccess(t: List<Banner>) {
                        _getBanners.postValue(t)
                    }
                })
        }
    }
}