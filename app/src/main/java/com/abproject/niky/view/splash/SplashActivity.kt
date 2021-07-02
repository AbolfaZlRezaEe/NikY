package com.abproject.niky.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.abproject.niky.databinding.ActivitySplashBinding
import com.abproject.niky.view.auth.AuthActivity
import com.abproject.niky.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeView()
    }

    private fun initializeView() {
        val result = splashViewModel.loadUserInformation()
        //set delay for start activity action
        Timer("startDestination", false).schedule(3000) {
            if (result) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                this@SplashActivity.finish()
            } else {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                this@SplashActivity.finish()
            }
        }

    }
}