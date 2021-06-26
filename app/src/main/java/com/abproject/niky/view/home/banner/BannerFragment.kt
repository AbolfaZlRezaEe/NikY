package com.abproject.niky.view.home.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abproject.niky.base.NikyFragment
import com.abproject.niky.components.imageview.ImageLoadingService
import com.abproject.niky.databinding.FragmentBannerBinding
import com.abproject.niky.model.model.Banner
import com.abproject.niky.utils.Variables.EXTRA_KEY_BANNER_DATA
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject

@AndroidEntryPoint
class BannerFragment : NikyFragment() {

    private var _binding: FragmentBannerBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imageLoadingService: ImageLoadingService

    companion object {
        /**
         * this function takes a key and banner in input
         * and generate an argument for BannerFragment.
         * so for use this function please use (EXTRA_KEY_BANNER_DATA)
         * for key
         */
        fun newInstance(
            key: String,
            banner: Banner,
        ): BannerFragment {
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(key, banner)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBannerBinding.inflate(
            inflater,
            container,
            false
        )
        //get banner argument from fragment argument.
        val banner =
            requireArguments().getParcelable<Banner>(EXTRA_KEY_BANNER_DATA)
                ?: throw IllegalStateException("debug (BannerFragment)-> Banner can not be null!")
        //load image with imageLoadingService (Fresco)
        imageLoadingService.loadImage(
            binding.root,
            banner.image
        )
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}