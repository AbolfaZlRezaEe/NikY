package com.abproject.niky.view.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.abproject.niky.R
import com.abproject.niky.base.NikyFragment
import com.abproject.niky.databinding.FragmentSignUpBinding
import com.abproject.niky.utils.other.isValidEmail
import com.abproject.niky.utils.rxjava.NikyCompletableObserver
import com.abproject.niky.view.auth.AuthViewModel
import com.abproject.niky.view.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class SignUpFragment : NikyFragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        listeningToTheObservers()
    }

    private fun listeningToTheObservers() {
        authViewModel.progressbarStatusLiveData.observe(viewLifecycleOwner) { show ->
            showProgressbar(show, true)
        }
    }

    private fun initializeViews() {
        binding.signInLinkMaterialButtonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.singUpMaterialButton.setOnClickListener {
            if (validationOfEditTexts()) {
                authViewModel.userSignUp(
                    binding.emailEditTextSignUp.text.toString(),
                    binding.passwordEditTextSignUp.text.toString()
                )?.subscribe(object : NikyCompletableObserver(authViewModel.compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.signUpSuccessfully))
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
        if (binding.emailEditTextSignUp.text.isEmpty()) {
            binding.emailEditTextSignUp.error = getString(R.string.pleaseEnterEmail)
        }
        if (!binding.emailEditTextSignUp.text.isValidEmail()) {
            binding.emailEditTextSignUp.error = getString(R.string.pleaseEnterValidEmail)
        }
        if (binding.passwordEditTextSignUp.text.isEmpty()) {
            binding.passwordEditTextSignUp.error = getString(R.string.pleaseEnterPassword)
        }
        if (binding.passwordEditTextSignUp.text.length < 8) {
            binding.passwordEditTextSignUp.error =
                getString(R.string.onlyPasswordsLongerThan8CharIsValid)
        }
    }

    private fun validationOfEditTexts(): Boolean {
        return binding.emailEditTextSignUp.text.isNotEmpty()
                && binding.emailEditTextSignUp.text.isValidEmail()
                && binding.passwordEditTextSignUp.text.isNotEmpty()
                && binding.passwordEditTextSignUp.text.length >= 8
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}