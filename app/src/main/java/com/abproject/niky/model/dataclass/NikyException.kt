package com.abproject.niky.model.dataclass

import androidx.annotation.StringRes
import com.abproject.niky.utils.exceptionhandler.ExceptionType

/**
 * this dataclass contain all exceptions type and messages
 * and holding data into NikyView.
 */
data class NikyException constructor(
    val exceptionType: ExceptionType,
    //this message can be full from string resources.
    @StringRes val resourceStringMessage: Int = 0,
    //this message just receive from server and show to the user.
    val exceptionMessage: String? = null,
) : Throwable()