package com.abproject.niky.view.views.auth

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
        _progressbarStatusLiveData.value = true
        return userRepository.signIn(
            username = username,
            password = password
        ).asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
    }

    fun userSignUp(
        username: String,
        password: String,
    ): Completable {
        _progressbarStatusLiveData.value = true
        return userRepository.signUp(
            username = username,
            password = password
        ).asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
    }
}