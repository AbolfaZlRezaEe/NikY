package com.abproject.niky.model.database.dao

import androidx.room.*
import com.abproject.niky.model.dataclass.Product
import io.reactivex.Completable
import io.reactivex.Single

/**
 * ProductDao is an interface that contain all action
 * about favorite products section in the application.
 */
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProductToFavorites(
        product: Product,
    ): Completable

    @Delete
    fun deleteProductFromFavorites(
        product: Product,
    ): Completable

    @Query("SELECT * FROM tbl_product")
    fun getAllFavoritesProduct(): Single<List<Product>>

    @Query("DELETE FROM tbl_product")
    fun deleteAllProductsFromFavorites(): Completable
}