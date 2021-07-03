package com.abproject.niky.view.auth

import android.os.Bundle
import androidx.activity.viewModels
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : NikyActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectionLiveData.observe(this) { status ->
            authViewModel.internetConnectionStatus.value = status
        }
    }
}