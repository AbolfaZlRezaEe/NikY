package com.abproject.niky.model.datasource.cart

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.dataclass.AddToCart
import com.abproject.niky.model.dataclass.Cart
import com.abproject.niky.model.dataclass.CartItemCount
import com.abproject.niky.model.dataclass.Message
import com.abproject.niky.utils.other.Variables.JSON_CART_ITEM_ID_KEY
import com.abproject.niky.utils.other.Variables.JSON_COUNT_KEY
import com.abproject.niky.utils.other.Variables.JSON_PRODUCT_ID_KEY
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor(
    private val apiService: NikyApiService,
) : CartDataSource {

    override fun addProductToCart(
        productId: Int,
    ): Single<AddToCart> {
        return apiService.addProductToCart(
            JsonObject().apply {
                //add product id into json body and send to the server
                addProperty(JSON_PRODUCT_ID_KEY, productId)
            }
        )
    }

    override fun getAllCarts(): Single<Cart> {
        return apiService.getCarts()
    }

    override fun removeProductFromCart(
        cartItemId: Int,
    ): Single<Message> {
        return apiService.removeProductFromCart(
            JsonObject().apply {
                //add cart item id into json body and send to the server
                addProperty(JSON_CART_ITEM_ID_KEY, cartItemId)
            }
        )
    }

    override fun changeProductCountFromCart(
        cartItemId: Int,
        count: Int,
    ): Single<AddToCart> {
        return apiService.changeProductCount(
            JsonObject().apply {
                addProperty(JSON_CART_ITEM_ID_KEY, cartItemId)
                //add count into json body and send to the server
                addProperty(JSON_COUNT_KEY, count)
            }
        )
    }

    override fun getCartItemCart(): Single<CartItemCount> {
        return apiService.getCartItemCount()
    }
}