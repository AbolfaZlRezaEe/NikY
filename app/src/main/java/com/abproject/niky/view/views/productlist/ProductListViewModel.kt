package com.abproject.niky.view.views.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.abproject.niky.R
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.Product
import com.abproject.niky.model.repository.product.ProductRepository
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_SORT
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikyCompletableObserver
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
) : NikyViewModel() {

    /**
     * this variable is responsible for passing sort title
     * to the view. with this variable you can access to the
     * four strings in string resource.
     */
    private val _getSortTitle = MutableLiveData<Int>()

    /**
     * but this variable only use for passing real sort that
     * received from savedStateHandle from HomeFragment.
     */
    private val _getSortState = MutableLiveData<Int>()
    private val _getProducts = MutableLiveData<List<Product>>()

    private val productSortTitles = arrayOf(
        R.string.sortLatestProduct,
        R.string.sortPopularProduct,
        R.string.sortPriceHighToLowProduct,
        R.string.sortPriceLowToHighProduct
    )

    val getSortTitle: LiveData<Int> get() = _getSortTitle
    val getProducts: LiveData<List<Product>> get() = _getProducts
    val getSortState: LiveData<Int> get() = _getSortState

    init {
        //get sort title from savedSateHandle.
        _getSortState.value = getSortFromSavedStateHandle()
        //get resource String by position of Array list.
        _getSortTitle.value = productSortTitles[_getSortState.value!!]
    }

    fun getProductsBySort(
        sort: Int,
    ) {
        _getSortState.postValue(sort)
        _getSortTitle.postValue(productSortTitles[_getSortState.value!!])
        _progressbarStatusLiveData.postValue(true)
        productRepository.getProductsBySort(sort)
            .asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
            .subscribe(object : NikySingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(response: List<Product>) {
                    _getProducts.value = response
                }
            })

    }

    /**
     * this function takes savedStateHandle from this class and
     * return an Int from the extra field in ProductListActivity.
     */
    private fun getSortFromSavedStateHandle(): Int {
        return savedStateHandle.get<Int>(EXTRA_KEY_PRODUCT_SORT)!!
    }

    fun addOrDeleteProductFromFavorites(
        product: Product,
    ) {
        if (product.isFavorite) {
            productRepository.deleteProductFromFavorite(product)
                .asyncNetworkRequest()
                .subscribe(object : NikyCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        } else {
            productRepository.addProductToFavorite(product)
                .asyncNetworkRequest()
                .subscribe(object : NikyCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })
        }
    }

}