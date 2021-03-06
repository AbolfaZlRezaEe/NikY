package com.abproject.niky.utils.exceptionhandler

/**
 * this for variable in enum class used to specify the type of errors.
 * SIMPLE -> just for showing a text that is defined in String resources.
 * TIMEOUT -> used for when internet connection lost or there is a problem
 * in internet connection.
 * DATABASE -> used for database errors and exceptions.
 * AUTH -> used for server errors like 401 unauthorized.
 * INTERNET_CONNECTION -> problem in the phone internet connection.
 * INCREASE_CART_ITEM, DECREASE_CART_ITEM -> related to cart adapter.
 */
enum class ExceptionType {
    SIMPLE,
    TIMEOUT,
    DATABASE,
    AUTH,
    INTERNET_CONNECTION,
    INCREASE_CART_ITEM,
    DECREASE_CART_ITEM
}