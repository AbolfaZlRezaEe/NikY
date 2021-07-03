package com.abproject.niky.view.auth

import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.repository.user.UserRepository
import com.abproject.niky.utils.other.asyncNetworkRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : NikyViewModel() {

    fun userSignIn(
        username: String,
        password: String,
    ): Completable? {
        return if (checkingInternetConnection()) {
            _progressbarStatus.value = true
            userRepository.signIn(
                username = username,
                password = password
            ).asyncNetworkRequest()
                .doFinally { _progressbarStatus.postValue(false) }
        } else {
            null
        }
    }

    fun userSignUp(
        username: String,
        password: String,
    ): Completable? {
        return if (checkingInternetConnection()) {
            _progressbarStatus.value = true
            return userRepository.signUp(
                username = username,
                password = password
            ).asyncNetworkRequest()
                .doFinally { _progressbarStatus.postValue(false) }
        } else {
            null
        }
    }
}