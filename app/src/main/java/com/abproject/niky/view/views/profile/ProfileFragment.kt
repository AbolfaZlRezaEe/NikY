package com.abproject.niky.view.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.abproject.niky.R
import com.abproject.niky.base.NikyFragment
import com.abproject.niky.databinding.FragmentProfileBinding
import com.abproject.niky.model.objects.UserContainer
import com.abproject.niky.utils.other.EnglishConverter
import com.abproject.niky.view.views.auth.AuthActivity
import com.abproject.niky.view.views.favoriteslist.FavoritesListActivity
import com.abproject.niky.view.views.orderhistory.OrderHistoryActivity
import com.abproject.niky.view.views.profiledetail.ProfileDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileFragment : NikyFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initializeViews()
    }

    private fun initializeViews() {
        if (UserContainer.firstName.isNullOrEmpty()) {
            binding.usernameTextViewProfileFragment.text = getString(R.string.newUser)
        } else {
            binding.usernameTextViewProfileFragment.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(
                    "${UserContainer.firstName} ${UserContainer.lastName}"
                )
        }

        binding.emailTextViewProfileFragment.text = UserContainer.email

        binding.applicationVersionTextViewProfileFragment.text =
            EnglishConverter.convertEnglishNumberToPersianNumber(getString(R.string.version))

        binding.profileEditTextViewFragmentProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), ProfileDetailActivity::class.java))
        }

        binding.favoritesListTextViewFragmentProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), FavoritesListActivity::class.java))
        }

        binding.orderHistoryTextViewFragmentProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), OrderHistoryActivity::class.java))
        }

        binding.signoutTextViewFragmentProfile.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.NikyAlertDialogStyle)
                .setTitle(getString(R.string.signOutDialogTitle))
                .setMessage(getString(R.string.signOutDialogMessage))
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    dialog.dismiss()
                    profileViewModel.signOut()
                    startActivity(Intent(requireActivity(), AuthActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}