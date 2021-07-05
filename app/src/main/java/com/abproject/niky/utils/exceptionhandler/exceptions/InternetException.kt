package com.abproject.niky.utils.exceptionhandler.exceptions

import androidx.annotation.StringRes
import com.abproject.niky.utils.exceptionhandler.ExceptionType

/**
 * this class contain all internet connection exceptions that thrown
 * in the NikyViewModel class.
 */
data class InternetException(
    val exceptionType: ExceptionType,
    @StringRes val resourceStringMessage: Int,
) : Exception()