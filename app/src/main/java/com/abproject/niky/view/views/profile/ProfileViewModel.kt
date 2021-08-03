package com.abproject.niky.view.views.profile

import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : NikyViewModel() {

    fun signOut() {
        userRepository.signOut()
    }
}