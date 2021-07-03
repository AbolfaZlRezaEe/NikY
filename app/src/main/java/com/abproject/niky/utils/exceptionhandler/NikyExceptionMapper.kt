package com.abproject.niky.utils.exceptionhandler

import com.abproject.niky.R
import com.abproject.niky.model.dataclass.NikyException
import com.abproject.niky.utils.other.Variables.EXCEPTION_MESSAGE_KEY
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.IllegalStateException
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
                    when (throwable.code()) {
                        //authentication error
                        401 -> {
                            //getting json body from exception
                            val errorJsonObject =
                                JSONObject(throwable.response()?.errorBody()!!.string())
                            //getting message key from json body
                            val errorMessage = errorJsonObject.getString(EXCEPTION_MESSAGE_KEY)
                            //only when this message thrown that username or password wrong
                            return if (errorMessage == "The user credentials were incorrect.")
                                NikyException(
                                    ExceptionType.SIMPLE,
                                    resourceStringMessage = R.string.usernameOrPasswordIsIncorrect
                                )
                            //this is the default message when thrown Http 401 exception
                            else {
                                NikyException(
                                    ExceptionType.AUTH,
                                    exceptionMessage = "$errorMessage!"
                                )
                            }
                        }
                        //internal server error (proxy set)
                        500 -> {
                            return NikyException(
                                ExceptionType.SIMPLE,
                                resourceStringMessage = R.string.errorReceivingInformation
                            )
                        }
                        //exciting username in server error
                        422 -> {
                            return NikyException(
                                ExceptionType.SIMPLE,
                                resourceStringMessage = R.string.signUpUsernameErrorMessage
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
                //this block only calls when internet connection was a problem
                is SocketTimeoutException -> {
                    return NikyException(
                        ExceptionType.TIMEOUT,
                        resourceStringMessage = R.string.timeoutExceptionMessage
                    )
                }
                //this block only calls when an internet connection lost or a problem
                is IllegalStateException -> {
                    return NikyException(
                        ExceptionType.SIMPLE,
                        resourceStringMessage = R.string.pleaseCheckYourInternetConnection
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