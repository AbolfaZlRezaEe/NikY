package com.abproject.niky.view.splash

import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.objects.TokenContainer
import com.abproject.niky.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : NikyViewModel() {

    /**
     * only this method responsible for checking exciting token
     * in shared preferences.
     */
    fun loadUserInformation(): Boolean {
        userRepository.loadTokenFromSharedPreferences()
        return !TokenContainer.accessToken.isNullOrEmpty()
    }
}