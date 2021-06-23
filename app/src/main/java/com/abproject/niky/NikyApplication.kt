package com.abproject.niky

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NikyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //for initializing Timber.
        Timber.plant(Timber.DebugTree())

        //for initializing Fresco
        Fresco.initialize(this)
    }
}