package com.abproject.niky.view.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.Comment
import com.abproject.niky.model.dataclass.Product
import com.abproject.niky.model.repository.cart.CartRepository
import com.abproject.niky.model.repository.comment.CommentRepository
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_DATA
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val commentRepository: CommentRepository,
    private val cartRepository: CartRepository,
) : NikyViewModel() {

    private val _getProduct = MutableLiveData<Product>()
    private val _getComments = MutableLiveData<List<Comment>>()

    val getProduct get() = _getProduct
    val getComments get() = _getComments

    init {
        getProductFromExtra()
        getComments()
    }

    /**
     * getProductFromExtra function take savedStateHandle and then
     * check the extra, if there was product object, take that and
     * return to the view for showing product detail.
     */
    private fun getProductFromExtra() {
        val response = savedStateHandle.get<Product>(EXTRA_KEY_PRODUCT_DATA)
        _getProduct.value = response!!
    }

    private fun getComments() {
        _progressbarStatus.postValue(true)
        commentRepository.getComments(_getProduct.value!!.id)
            .asyncNetworkRequest()
            .doFinally { _progressbarStatus.postValue(false) }
            .subscribe(object : NikySingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(response: List<Comment>) {
                    _getComments.postValue(response)
                }
            })
    }

    /**
     * this function convert single request to the
     * completable request and then sending request to the
     * server for adding product tp the cart.
     */
     fun addProductToCart(): Completable {
        return cartRepository.addProductToCart(_getProduct.value!!.id)
            .asyncNetworkRequest()
            .ignoreElement()
    }

}