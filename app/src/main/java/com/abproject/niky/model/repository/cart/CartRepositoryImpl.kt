package com.abproject.niky.model.repository.cart

import com.abproject.niky.model.datasource.cart.CartDataSource
import com.abproject.niky.model.dataclass.AddToCart
import com.abproject.niky.model.dataclass.Cart
import com.abproject.niky.model.dataclass.CartItemCount
import com.abproject.niky.model.dataclass.Message
import io.reactivex.Single
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartRemoteDataSource: CartDataSource,
) : CartRepository {

    override fun addProductToCart(
        productId: Int,
    ): Single<AddToCart> {
        return cartRemoteDataSource.addProductToCart(productId)
    }

    override fun getAllCarts(): Single<Cart> {
        return cartRemoteDataSource.getAllCarts()
    }

    override fun removeProductFromCart(
        cartItemId: Int,
    ): Single<Message> {
        return cartRemoteDataSource.removeProductFromCart(cartItemId)
    }

    override fun changeProductCountFromCart(
        cartItemId: Int,
        count: Int,
    ): Single<AddToCart> {
        return cartRemoteDataSource.changeProductCountFromCart(cartItemId, count)
    }

    override fun getCartItemCount(): Single<CartItemCount> {
        return cartRemoteDataSource.getCartItemCart()
    }
}