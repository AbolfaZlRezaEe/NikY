package com.abproject.niky.model.apiservice

import com.abproject.niky.model.dataclass.*
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * this Interface is responsible for sending all requests to the server.
 * also NikyApiService work with Rxjava library for receive data from
 * server.
 */
interface NikyApiService {

    //get product
    @GET("product/list")
    fun getProducts(
        @Query("sort") sort: String,
    ): Single<List<Product>>

    //get banners
    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>

    //get comments
    @GET("comment/list")
    fun getComments(
        @Query("product_id") productId: Int,
    ): Single<List<Comment>>

    //add product to cart
    @POST("cart/add")
    fun addProductToCart(
        @Body jsonObject: JsonObject,
    ): Single<AddToCart>

    //remove product from cart
    @POST("cart/remove")
    fun removeProductFromCart(
        @Body jsonObject: JsonObject,
    ): Single<Message>

    //get all carts
    @GET("cart/list")
    fun getCarts(): Single<Cart>

    //change product item count
    @POST("cart/changeCount")
    fun changeProductCount(
        @Body jsonObject: JsonObject,
    ): Single<AddToCart>

    //get cart item count
    @GET("cart/count")
    fun getCartItemCount(): Single<CartItemCount>

    //user signin
    @POST("auth/token")
    fun signIn(
        @Body jsonObject: JsonObject,
    ): Single<Token>

    //user signup
    @POST("user/register")
    fun signUp(
        @Body jsonObject: JsonObject,
    ): Single<Message>

    //refresh token
    @POST("auth/token")
    fun refreshToken(
        @Body jsonObject: JsonObject,
    ): Call<Token>
}