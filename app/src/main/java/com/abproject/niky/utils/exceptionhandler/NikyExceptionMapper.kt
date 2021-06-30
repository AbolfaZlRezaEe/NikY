package com.abproject.niky.utils.exceptionhandler

import com.abproject.niky.R
import com.abproject.niky.model.model.NikyException
import com.abproject.niky.utils.Variables.EXCEPTION_MESSAGE_KEY
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * NikyExceptionMapper contained all exceptions and classifies all exceptions.
 */
class NikyExceptionMapper {

    companion object {

        /**
         * with this method you can Classifies exceptions and then return
         * NikyExceptions that you can use in views or NikyView base class.
         */
        fun map(
            throwable: Throwable,
        ): NikyException {
            return when (throwable) {
                is HttpException -> {
                    //getting json body from exception
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    //getting message key from json body
                    val errorMessage = errorJsonObject.getString(EXCEPTION_MESSAGE_KEY)
                    return NikyException(
                        ExceptionType.AUTH,
                        exceptionMessage = "$errorMessage!"
                    )
                }
                //this block only calls when internet connection was a problem
                is SocketTimeoutException -> {
                    return NikyException(
                        ExceptionType.TIMEOUT,
                        resourceStringMessage = R.string.timeoutExceptionMessage
                    )
                }
                else -> {
                    return NikyException(
                        ExceptionType.SIMPLE,
                        resourceStringMessage = R.string.snackbarExceptionMessage
                    )
                }
            }
        }
    }
}