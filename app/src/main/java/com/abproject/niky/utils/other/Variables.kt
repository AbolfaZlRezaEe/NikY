package com.abproject.niky.utils.other

object Variables {

    //base url use for creating retrofit instance
    const val BASE_URL = "http://expertdevelopers.ir/api/v1/"

    //database name use for creating room database instance.
    const val DATABASE_NAME = "niky_db"

    //name of the Product table in database.
    const val PRODUCT_TABLE_NAME = "tbl_product"

    //name of the sharedPrefs
    const val SHARED_PREFERENCES_NAME = "niky_application"

    //extra keys use for intents in views.
    const val EXTRA_KEY_BANNER_DATA = "bannerData"
    const val EXTRA_KEY_PRODUCT_DATA = "productData"
    const val EXTRA_KEY_PRODUCT_ID_DATA = "productIdData"
    const val EXTRA_KEY_PRODUCT_SORT = "productSort"
    const val EXTRA_KEY_PURCHASE_DETAIL = "purchaseDetail"
    const val EXTRA_KEY_ORDER_ID = "orderId"
    const val EXTRA_KEY_PRODUCT_ID = "productId"
    const val EXTRA_KEY_COMMENT = "comment"
    const val EXTRA_KEY_ORDER_HISTORY_ITEM = "orderHistoryItem"

    //static variables for comments
    const val REQUEST_COMMENT_KEY = 127

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

    //exception json objects keys
    const val EXCEPTION_MESSAGE_KEY = "message"

    //json keys for sending parameters to the server
    const val JSON_PRODUCT_ID_KEY = "product_id"
    const val JSON_CART_ITEM_ID_KEY = "cart_item_id"
    const val JSON_COUNT_KEY = "count"
    const val JSON_USERNAME_KEY = "username"
    const val JSON_EMAIL_KEY = "email"
    const val JSON_PASSWORD_KEY = "password"
    const val JSON_GRANT_TYPE_KEY = "grant_type"
    const val JSON_CLIENT_ID_KEY = "client_id"
    const val JSON_CLIENT_SECRET_KEY = "client_secret"
    const val JSON_REFRESH_TOKEN_KEY = "refresh_token"
    const val JSON_FIRST_NAME_KEY = "first_name"
    const val JSON_LAST_NAME_KEY = "last_name"
    const val JSON_POSTAL_CODE_KEY = "postal_code"
    const val JSON_PHONE_NUMBER_KEY = "mobile"
    const val JSON_ADDRESS_KEY = "address"
    const val JSON_PAYMENT_METHOD_KEY = "payment_method"
    const val JSON_ORDER_ID_KEY = "order_id"
    const val JSON_TITLE_KEY = "title"
    const val JSON_CONTENT_KEY = "content"

    //json values for sending parameters to the server
    const val JSON_GRANT_TYPE_VALUE = "password"
    const val JSON_CLIENT_ID_VALUE = 2
    const val JSON_REFRESH_TOKEN_VALUE = "refresh_token"
    const val JSON_CLIENT_SECRET_VALUE = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

    //request headers keys
    const val HEADER_REQUEST_KEY_AUTHORIZATION = "Authorization"
    const val HEADER_REQUEST_KEY_ACCEPT = "Accept"

    //request header values
    const val HEADER_REQUEST_VALUE_JSON = "application/json"

    //shared preferences keys
    const val SHARED_TOKEN_TYPE_KEY = "token_type"
    const val SHARED_ACCESS_TOKE_KEY = "access_token"
    const val SHARED_REFRESH_TOKEN_KEY = "refresh_token"
    const val SHARED_FIRST_NAME_KEY = "first_name"
    const val SHARED_LAST_NAME_KEY = "last_name"
    const val SHARED_EMAIL_KEY = "email"
    const val SHARED_PHONE_NUMBER_KEY = "phone_number"
    const val SHARED_POSTAL_CODE_KEY = "postal_code"
    const val SHARED_ADDRESS_KEY = "address"
    const val SHARED_AGE_KEY = "age"

    //timer name
    const val TIMER_SEND_CONNECTION_STATUS = "sendConnectionStatus"

    //cart adapter view types
    const val VIEW_TYPE_CART_ITEM = 50
    const val VIEW_TYPE_PURCHASE_DETAIL = 51

    //change cart item count type
    const val INCREASE_CART_ITEM = 52
    const val DECREASE_CART_ITEM = 53

    //payment method
    const val PAYMENT_METHOD_ONLINE = "online"
    const val PAYMENT_METHOD_CASH_ON_DELIVERY = "cash_on_delivery"
}