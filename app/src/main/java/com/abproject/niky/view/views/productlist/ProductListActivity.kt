package com.abproject.niky.view.views.productlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityProductListBinding
import com.abproject.niky.model.dataclass.Product
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_DATA
import com.abproject.niky.utils.other.Variables.PRODUCT_VIEW_TYPE_GRID
import com.abproject.niky.utils.other.Variables.PRODUCT_VIEW_TYPE_LARGE
import com.abproject.niky.view.views.common.ProductAdapter
import com.abproject.niky.view.views.productdetail.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductListActivity : NikyActivity(), ProductAdapter.ProductListener {

    private lateinit var binding: ActivityProductListBinding
    private val productListViewModel: ProductListViewModel by viewModels()
    private lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productListViewModel.getProductsBySort(productListViewModel.getSortState.value!!)

        listeningToObservers()

        initializeViews()
    }

    private fun listeningToObservers() {
        productListViewModel.getSortTitle.observe(this) { stringResource ->
            binding.productSortTextViewProductList.text = getString(stringResource)
        }

        productListViewModel.progressbarStatusLiveData.observe(this) { show ->
            showProgressbar(show)
        }

        productListViewModel.getProducts.observe(this) { response ->
            productAdapter.setProducts(response)
        }
    }

    private fun initializeViews() {
        //initialize recyclerView
        productAdapter.productListener = this
        productAdapter.setViewType(PRODUCT_VIEW_TYPE_GRID)
        gridLayoutManager = GridLayoutManager(
            this,
            2
        )
        binding.productRecyclerViewProductList.layoutManager = gridLayoutManager
        binding.productRecyclerViewProductList.adapter = productAdapter

        //setup back button click
        binding.toolbarViewProductList.onBackButtonClickListener = View.OnClickListener {
            this.finish()
        }

        //initialize choose sort button click
        binding.chooseSortViewProductList.setOnClickListener {
            MaterialAlertDialogBuilder(this,R.style.NikyAlertDialogStyle)
                .setTitle(getString(R.string.sortBy))
                .setSingleChoiceItems(
                    R.array.productSortArray,
                    productListViewModel.getSortState.value!!
                ) { dialog, selectedSortIndex ->
                    productListViewModel.getProductsBySort(selectedSortIndex)
                    dialog.dismiss()
                }
                .show()
        }

        //initialize view type button click
        binding.viewTypeImageViewProductList.setOnClickListener {
            if (productAdapter.getViewType() == PRODUCT_VIEW_TYPE_GRID) {
                productAdapter.setViewType(PRODUCT_VIEW_TYPE_LARGE)
                binding.viewTypeImageViewProductList.setImageResource(R.drawable.ic_product_large_24dp)
                gridLayoutManager.spanCount = 1
                productAdapter.notifyDataSetChanged()
            } else {
                productAdapter.setViewType(PRODUCT_VIEW_TYPE_GRID)
                binding.viewTypeImageViewProductList.setImageResource(R.drawable.ic_product_grid_24dp)
                gridLayoutManager.spanCount = 2
                productAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_PRODUCT_DATA, product)
        })
    }

    override fun onFavoriteButtonClick(product: Product) {
        productListViewModel.addOrDeleteProductFromFavorites(product)
    }

}