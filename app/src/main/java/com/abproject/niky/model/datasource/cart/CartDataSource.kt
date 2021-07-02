package com.abproject.niky.model.datasource.cart

import com.abproject.niky.model.dataclass.*
import io.reactivex.Single

interface CartDataSource {

    fun addProductToCart(
        productId: Int,
    ): Single<AddToCart>

    fun getAllCarts(): Single<Cart>

    fun removeProductFromCart(
        cartItemId: Int,
    ): Single<Message>

    fun changeProductCountFromCart(
        cartItemId: Int,
        count: Int,
    ): Single<AddToCart>

    fun getCartItemCart(): Single<CartItemCount>
}