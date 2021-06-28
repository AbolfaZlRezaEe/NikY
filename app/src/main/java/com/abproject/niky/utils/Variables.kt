package com.abproject.niky.utils

object Variables {

    //base url use for creating retrofit instance
    const val BASE_URL = "http://expertdevelopers.ir/api/v1/"

    //database name use for creating room database instance.
    const val DATABASE_NAME = "niky_db"

    //name of the Product table in database.
    const val PRODUCT_TABLE_NAME = "tbl_product"

    //extra keys use for intents in views.
    const val EXTRA_KEY_BANNER_DATA = "bannerData"
    const val EXTRA_KEY_PRODUCT_DATA = "productData"
    const val EXTRA_KEY_PRODUCT_ID_DATA = "productIdData"
    const val EXTRA_KEY_PRODUCT_SORT = "productSort"

    //all static functions can use in sending request for
    //receive Product list from server
    const val PRODUCT_SORT_LATEST = 0
    const val PRODUCT_SORT_POPULAR = 1
    const val PRODUCT_SORT_PRICE_DESC = 2
    const val PRODUCT_SORT_PRICE_ASC = 3

    //static variables for comment adapter
    const val SHOW_EVERY_THINGS = 125
    const val SHOW_THREE_COMMENTS = 126

    //view types for product adapter
    const val PRODUCT_VIEW_TYPE_LARGE = 4
    const val PRODUCT_VIEW_TYPE_ROUNDED = 5
    const val PRODUCT_VIEW_TYPE_GRID = 6
}