package com.abproject.niky.view.views.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.abproject.niky.R
import com.abproject.niky.base.NikyFragment
import com.abproject.niky.databinding.FragmentSignInBinding
import com.abproject.niky.utils.other.isValidEmail
import com.abproject.niky.utils.rxjava.NikyCompletableObserver
import com.abproject.niky.view.views.auth.AuthViewModel
import com.abproject.niky.view.views.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class SignInFragment : NikyFragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        listeningToTheObservers()
    }

    private fun listeningToTheObservers() {
        authViewModel.progressbarStatusLiveData.observe(viewLifecycleOwner) { showingState ->
            if (showingState) {
                binding.progressBarSignInButton.visibility = View.VISIBLE
                binding.singInMaterialButton.isEnabled = false
                binding.singInMaterialButton.text = ""
            } else {
                binding.progressBarSignInButton.visibility = View.GONE
                binding.singInMaterialButton.isEnabled = true
                binding.singInMaterialButton.text = getString(R.string.singIn)
            }
        }
    }

    private fun initializeViews() {
        binding.signUpLinkMaterialButtonSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.singInMaterialButton.setOnClickListener {
            if (validationOfEditTexts()) {
                authViewModel.userSignIn(
                    binding.emailEditTextSignIn.text.toString(),
                    binding.passwordEditTextSignIn.text.toString()
                )?.subscribe(object : NikyCompletableObserver(authViewModel.compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.signInSuccessfully))
                        Timer("startSplashActivity", false).schedule(3000) {
                            startActivity(Intent(requireContext(), SplashActivity::class.java))
                            requireActivity().finish()
                        }
                    }
                })
            } else
                setErrorForEditTexts()
        }
    }

    private fun setErrorForEditTexts() {
        if (binding.emailEditTextSignIn.text.isEmpty()) {
            binding.emailEditTextSignIn.error = getString(R.string.pleaseEnterEmail)
        }
        if (!binding.emailEditTextSignIn.text.isValidEmail()) {
            binding.emailEditTextSignIn.error = getString(R.string.pleaseEnterValidEmail)
        }
        if (binding.passwordEditTextSignIn.text.isEmpty()) {
            binding.passwordEditTextSignIn.error = getString(R.string.pleaseEnterPassword)
        }
        if (binding.passwordEditTextSignIn.text.length < 8) {
            binding.passwordEditTextSignIn.error =
                getString(R.string.onlyPasswordsLongerThan8CharIsValid)
        }
    }

    private fun validationOfEditTexts(): Boolean {
        return binding.emailEditTextSignIn.text.isNotEmpty()
                && binding.emailEditTextSignIn.text.isValidEmail()
                && binding.passwordEditTextSignIn.text.isNotEmpty()
                && binding.passwordEditTextSignIn.text.length >= 8
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}