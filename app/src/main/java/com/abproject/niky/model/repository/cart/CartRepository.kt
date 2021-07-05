package com.abproject.niky.model.repository.cart

import com.abproject.niky.model.dataclass.*
import io.reactivex.Single

interface CartRepository {

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

    fun getCartItemCount(): Single<CartItemCount>
}