package com.abproject.niky.view.favoriteslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityFavoritesListBinding
import com.abproject.niky.utils.other.Variables
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_DATA
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_LATEST
import com.abproject.niky.utils.rxjava.NikyCompletableObserver
import com.abproject.niky.view.auth.AuthActivity
import com.abproject.niky.view.productdetail.ProductDetailActivity
import com.abproject.niky.view.productlist.ProductListActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@AndroidEntryPoint
class FavoritesListActivity : NikyActivity() {

    private lateinit var binding: ActivityFavoritesListBinding
    private val favoriteListViewModel: FavoriteListViewModel by viewModels()

    @Inject
    lateinit var favoriteListAdapter: FavoriteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        listeningToTheObservers()
    }

    private fun initializeViews() {
        binding.recyclerViewFavoriteList.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerViewFavoriteList.adapter = favoriteListAdapter

        binding.toolbarFavoriteList.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
        binding.addProductsToFavoriteFloatingActionButton.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java).apply {
                putExtra(Variables.EXTRA_KEY_PRODUCT_SORT, PRODUCT_SORT_LATEST)
            })
        }
        binding.deleteAllProductsFromFavoritesImageView.setOnClickListener {
            MaterialAlertDialogBuilder(this, R.style.NikyAlertDialogStyle)
                .setTitle(getString(R.string.deleteAllFavoritesTitle))
                .setMessage(getString(R.string.deleteAllFavoritesMessage))
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    dialog.dismiss()
                    deleteAllUsers()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun deleteAllUsers() {
        favoriteListViewModel.deleteAllProductsFromFavorite()
            .subscribe(object :
                NikyCompletableObserver(favoriteListViewModel.compositeDisposable) {
                override fun onComplete() {
                    showSnackBar(getString(R.string.deleteAllProductsFromFavoriteSuccessfully))
                }
            })
    }

    override fun onStart() {
        super.onStart()
        favoriteListViewModel.getAllProductsFromFavorites()
    }

    private fun listeningToTheObservers() {
        favoriteListViewModel.emptyStateStatusLiveData.observe(this) { emptyState ->
            //create empty state view
            val emptyStateView = showEmptyState(R.layout.view_default_empty_state, R.id.emptyView)
            if (emptyState.mustShowEmptyState) {
                emptyStateView?.let { view ->
                    //set values if mustShowEmptyState is true
                    view.findViewById<TextView>(R.id.emptyStateMessageTextView).text =
                        getString(emptyState.emptyStateMessage)
                }
            } else {
                emptyStateView?.visibility = View.INVISIBLE
            }
        }
        favoriteListViewModel.getAllFavoriteProducts.observe(this) { products ->
            favoriteListAdapter.setData(products)
            binding.deleteAllProductsFromFavoritesImageView.visibility =
                if (products.size <= 1) View.GONE else View.VISIBLE

        }
        favoriteListAdapter.onProductClick = { product ->
            //start activity and pass product to ProductDetailActivity
            startActivity(Intent(this, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_KEY_PRODUCT_DATA, product)
            })
        }
        favoriteListAdapter.onDeleteButtonClick = { product ->
            favoriteListViewModel.deleteProductFromFavorites(product)
                .subscribe(object :
                    NikyCompletableObserver(favoriteListViewModel.compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.deleteProductFromFavoriteSuccessfully))
                        Timber.i(favoriteListViewModel.getAllFavoriteProducts.value!!.size.toString())
                    }
                })
        }
    }
}