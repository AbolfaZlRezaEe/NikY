package com.abproject.niky

import android.app.Application
import com.abproject.niky.model.repository.user.UserRepository
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class NikyApplication : Application() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        //for initializing Timber.
        Timber.plant(Timber.DebugTree())

        //for initializing Fresco
        Fresco.initialize(this)

        //for loading user information if exist
        userRepository.loadUserInformation()
    }
}