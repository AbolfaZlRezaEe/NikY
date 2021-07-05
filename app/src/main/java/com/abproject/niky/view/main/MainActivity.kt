package com.abproject.niky.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityMainBinding
import com.abproject.niky.model.dataclass.CartItemCount
import com.abproject.niky.utils.other.UtilFunctions.convertDpToPixel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class MainActivity : NikyActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationView()

    }

    /**
     * functionality of this method is an copy of NavigationAdvancedSample.
     * please read and use this repository for own project that use bottom
     * navigation view.
     * https://github.com/android/architecture-components-samples/tree/main/NavigationAdvancedSample
     */
    private fun setupBottomNavigationView() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.navigationContainer
        ) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNaviagtionMain)
        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home, R.id.cart, R.id.profile)
        )
    }

    /**
     * getCartItemsCountBadge call by EventBus and when this function
     * call, bottom navigation create or change the badge of cart section.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getCartItemsCountBadge(cartItemCount: CartItemCount) {
        //create a badge for cart or getting that
        val badge = binding.bottomNaviagtionMain.getOrCreateBadge(R.id.cart)
        //set gravity for badge
        badge.badgeGravity = BadgeDrawable.BOTTOM_START
        //set colorPrimary as background color for badge
        badge.backgroundColor = MaterialColors.getColor(
            binding.bottomNaviagtionMain,
            R.attr.colorPrimary
        )
        //set verticalOffset for change the position of badge in center_vertical | center
        badge.verticalOffset = convertDpToPixel(10f, this).toInt()
        //ser number of badge
        badge.number = cartItemCount.count
        //set visibility of badge
        badge.isVisible = cartItemCount.count > 0
    }

    override fun onResume() {
        super.onResume()
        //this line calling for update the CartItemCount badge
        mainViewModel.getCartItemCount()
    }
}