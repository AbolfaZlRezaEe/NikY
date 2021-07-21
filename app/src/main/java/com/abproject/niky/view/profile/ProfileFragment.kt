package com.abproject.niky.view.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.abproject.niky.R
import com.abproject.niky.base.NikyFragment
import com.abproject.niky.databinding.FragmentProfileBinding
import com.abproject.niky.model.objects.UserContainer
import com.abproject.niky.utils.other.EnglishConverter
import com.abproject.niky.view.auth.AuthActivity
import com.abproject.niky.view.favoriteslist.FavoritesListActivity
import com.abproject.niky.view.orderhistory.OrderHistoryActivity
import com.abproject.niky.view.profiledetail.ProfileDetailActivity
import com.facebook.drawee.generic.RoundingParams
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

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
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.signOutDialogTitle))
                .setMessage(getString(R.string.signOutDialogMessage))
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    dialog.dismiss()
                    profileViewModel.signOut()
                    showSnackBar(getString(R.string.youSignOutFromAccount))
                    Timer("startAuthActivity", false).schedule(3000) {
                        startActivity(Intent(requireActivity(), AuthActivity::class.java))
                        requireActivity().finish()
                    }
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