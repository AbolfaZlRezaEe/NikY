package com.abproject.niky.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abproject.niky.R
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.*
import com.abproject.niky.model.repository.cart.CartRepository
import com.abproject.niky.utils.other.Variables.INCREASE_CART_ITEM
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
) : NikyViewModel() {

    private val _cartItemsLiveData = MutableLiveData<List<CartItem>>()
    private val _purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()

    val cartItemsLiveData: LiveData<List<CartItem>> get() = _cartItemsLiveData
    val purchaseDetailLiveData: LiveData<PurchaseDetail> get() = _purchaseDetailLiveData

    /*
    this variable responsible for contain how refresh data in CartFragment.
    for this, please read the onStart and onStop section in the CartFragment.
     */
    var forceForSendingRequests: Int = 0

    fun getCartItems() {
        processOfEmptyStateStatus()
        _progressbarStatus.value = true
        cartRepository.getAllCarts()
            .asyncNetworkRequest()
            .doFinally { _progressbarStatus.postValue(false) }
            .subscribe(object : NikySingleObserver<Cart>(compositeDisposable) {
                override fun onSuccess(response: Cart) {
                    _cartItemsLiveData.value = response.cartItems
                    processOfEmptyStateStatus()
                    setPurchaseDetailLiveData(
                        totalPrice = response.totalPrice,
                        shippingCost = response.shippingCost,
                        payablePrice = response.payable_price
                    )

                }
            })
    }

    private fun setPurchaseDetailLiveData(
        totalPrice: Long,
        shippingCost: Long,
        payablePrice: Long,
    ) {
        _purchaseDetailLiveData.value = PurchaseDetail(
            totalPrice = totalPrice,
            shippingCost = shippingCost,
            payablePrice = payablePrice
        )
    }

    fun removeProductFromCart(
        cartItem: CartItem,
    ): Completable? {
        return if (checkingInternetConnection()) {
            _progressbarStatus.value = true
            cartRepository.removeProductFromCart(cartItem.cartItemId)
                .asyncNetworkRequest()
                .doFinally { _progressbarStatus.postValue(false) }
                .doAfterSuccess {
                    calculatePurchaseDetail()

                    processOfEmptyStateStatus()

                    //publish new CartItemCount with EventBus
                    EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                        ?.let { cartItemCount ->
                            cartItemCount.count -= cartItem.count
                            EventBus.getDefault().postSticky(cartItemCount)
                        }

                }.ignoreElement()
        } else
            null
    }

    fun changeCartItemCount(
        cartItem: CartItem,
        changeCountType: Int,
    ): Completable? {
        return if (checkingInternetConnection()) {
            cartRepository.changeProductCountFromCart(
                cartItemId = cartItem.cartItemId,
                count = if (changeCountType == INCREASE_CART_ITEM) ++cartItem.count else --cartItem.count
            ).asyncNetworkRequest()
                .doAfterSuccess {
                    calculatePurchaseDetail()

                    processOfEmptyStateStatus()

                    //publish new CartItemCount with EventBus
                    EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                        ?.let { cartItemCount ->
                            if (changeCountType == INCREASE_CART_ITEM)
                                cartItemCount.count ++
                            else
                                cartItemCount.count --
                            EventBus.getDefault().postSticky(cartItemCount)
                        }

                }.ignoreElement()
        } else
            null
    }

    /**
     * processOfEmptyStateStatus responsible for contain empty state status
     * in cart fragment.
     * this functionality checking the cartItemLiveData for that.
     */
    private fun processOfEmptyStateStatus(
    ) {
        cartItemsLiveData.value?.let { data ->
            if (data.isEmpty())
                _emptyStateStatusLiveData.postValue(EmptyState(
                    true,
                    R.string.nothingForShowHere
                ))
            else
                _emptyStateStatusLiveData.postValue(EmptyState(false))
        }
    }

    /**
     * calculate Purchase detail for cart section with payable price and
     * total price.
     */
    private fun calculatePurchaseDetail() {
        _cartItemsLiveData.value?.let { carts ->
            _purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0L
                var payablePrice = 0L
                carts.forEach { cartItem ->
                    totalPrice += cartItem.product.currentPrice * cartItem.count
                    payablePrice += (cartItem.product.currentPrice - cartItem.product.discount) * cartItem.count
                }

                purchaseDetail.totalPrice = totalPrice
                purchaseDetail.payablePrice = payablePrice
                _purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }
    }
}