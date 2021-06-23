package com.abproject.niky.view.home.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abproject.niky.model.model.Banner
import com.abproject.niky.utils.Variables.EXTRA_KEY_BANNER_DATA

class BannerSliderAdapter(
    fragment: Fragment,
    private val banners: List<Banner>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return banners.size
    }

    override fun createFragment(position: Int): Fragment {
        return BannerFragment.newInstance(EXTRA_KEY_BANNER_DATA, banners[position])
    }
}