package com.abproject.niky.utils

object Variables {
    //base url use for creating retrofit instance
    const val BASE_URL = "http://expertdevelopers.ir/api/v1/"
    //database name use for creating RoomDatabase instance.
    const val DATABASE_NAME = "niky_db"
    //name of the Product table in database.
    const val PRODUCT_TABLE_NAME = "tbl_product"
    //extra keys use for intents in views.
    const val EXTRA_KEY_BANNER_DATA = "bannerData"
    //all static functions can use in sending request for
    //receive Product list from server
    const val PRODUCT_SORT_LATEST = 0
    const val PRODUCT_SORT_POPULAR = 1
    const val PRODUCT_SORT_PRICE_DESC = 2
    const val PRODUCT_SORT_PRICE_ASC = 3
}