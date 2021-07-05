package com.abproject.niky.utils.exceptionhandler.exceptions

import androidx.annotation.StringRes
import com.abproject.niky.utils.exceptionhandler.ExceptionType
import java.lang.Exception

/**
 * this class is obliged to contain all exceptions that
 * thrown by CartViewModel.
 */
data class CartException(
    val exceptionType: ExceptionType,
    @StringRes val resourceStringMessage: Int,
) : Exception() {
}