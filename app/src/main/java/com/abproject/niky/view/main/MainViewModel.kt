package com.abproject.niky.view.main

import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.CartItemCount
import com.abproject.niky.model.repository.cart.CartRepository
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartRepository: CartRepository,
) : NikyViewModel() {

    /**
     * this functionality getting all cartItemsCount and then publish that
     * with EventBus.
     */
    fun getCartItemCount() {
        cartRepository.getCartItemCount()
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikySingleObserver<CartItemCount>(compositeDisposable) {
                override fun onSuccess(response: CartItemCount) {
                    EventBus.getDefault().postSticky(response)
                }
            })
    }
}
