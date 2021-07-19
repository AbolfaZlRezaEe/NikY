package com.abproject.niky.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.abproject.niky.base.NikyFragment
import com.abproject.niky.databinding.FragmentHomeBinding
import com.abproject.niky.model.dataclass.Banner
import com.abproject.niky.model.dataclass.Product
import com.abproject.niky.utils.other.UtilFunctions.convertDpToPixel
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_DATA
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_SORT
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_LATEST
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_POPULAR
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_PRICE_ASC
import com.abproject.niky.utils.other.Variables.PRODUCT_SORT_PRICE_DESC
import com.abproject.niky.view.common.ProductAdapter
import com.abproject.niky.view.home.banner.BannerSliderAdapter
import com.abproject.niky.view.productdetail.ProductDetailActivity
import com.abproject.niky.view.productlist.ProductListActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : NikyFragment(), ProductAdapter.ProductListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    /**
     * it's so bad. but i have no choice.
     * create a 4 instance of ProductHomeAdapter
     * for each of the lists.
     */
    @Inject
    lateinit var popularAdapter: ProductAdapter

    @Inject
    lateinit var latestAdapter: ProductAdapter

    @Inject
    lateinit var priceAscAdapter: ProductAdapter

    @Inject
    lateinit var priceDescAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeningToObservers()

        homeViewModel.requestForReceiveProducts()
        homeViewModel.getAllBanners()

        recyclerViewsStateRestoration()

        initializeViewAllButtons()
    }

    /**
     * recyclerViewsStateRestoration function managing all
     * recyclerViews states in fragment.
     */
    private fun recyclerViewsStateRestoration() {
        latestAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        popularAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        priceAscAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        priceDescAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.ALLOW
    }

    private fun initializeViewAllButtons() {
        binding.latestViewAllButtonHome.setOnClickListener {
            startProductListActivity(PRODUCT_SORT_LATEST)
        }

        binding.popularViewAllButtonHome.setOnClickListener {
            startProductListActivity(PRODUCT_SORT_POPULAR)
        }

        binding.priceAscViewAllButtonHome.setOnClickListener {
            startProductListActivity(PRODUCT_SORT_PRICE_ASC)
        }

        binding.priceDescViewAllButtonHome.setOnClickListener {
            startProductListActivity(PRODUCT_SORT_PRICE_DESC)
        }
    }

    private fun startProductListActivity(
        productSort: Int,
    ) {
        startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
            putExtra(EXTRA_KEY_PRODUCT_SORT, productSort)
        })
    }

    //listening to all observers that this class needs.
    private fun listeningToObservers() {
        homeViewModel.progressbarStatusLiveData.observe(viewLifecycleOwner) { status ->
            //showing progressbar in view.
            showProgressbar(status)
        }

        homeViewModel.getLatestProducts.observe(viewLifecycleOwner) { response ->
            initializeRecyclerViews(PRODUCT_SORT_LATEST, response)
        }

        homeViewModel.getPopularProducts.observe(viewLifecycleOwner) { response ->
            initializeRecyclerViews(PRODUCT_SORT_POPULAR, response)
        }

        homeViewModel.getPriceAscProducts.observe(viewLifecycleOwner) { response ->
            initializeRecyclerViews(PRODUCT_SORT_PRICE_ASC, response)
        }

        homeViewModel.getPriceDescProducts.observe(viewLifecycleOwner) { response ->
            initializeRecyclerViews(PRODUCT_SORT_PRICE_DESC, response)
        }

        homeViewModel.getBanners.observe(viewLifecycleOwner) { response ->
            initializeBannerSlider(response, homeViewModel.viewPagerHeight)
        }
    }


    /**
     * this is so bad method. i have no choice right now.
     * this method takes a sort and list of the products
     * and then check sort of the list, then initialize
     * recyclerView for showing items into them.
     */
    private fun initializeRecyclerViews(
        sortBy: Int,
        products: List<Product>,
    ) {
        //initialize all productListeners for onClickListener
        latestAdapter.productListener = this
        popularAdapter.productListener = this
        priceAscAdapter.productListener = this
        priceDescAdapter.productListener = this

        when (sortBy) {
            PRODUCT_SORT_LATEST -> {
                latestAdapter.setProducts(products)
                binding.latestProductRecyclerViewHome.layoutManager =
                    LinearLayoutManager(requireContext(), HORIZONTAL, false)
                binding.latestProductRecyclerViewHome.adapter = latestAdapter
            }
            PRODUCT_SORT_POPULAR -> {
                popularAdapter.setProducts(products)
                binding.popularProductRecyclerViewHome.layoutManager =
                    LinearLayoutManager(requireContext(), HORIZONTAL, false)
                binding.popularProductRecyclerViewHome.adapter = popularAdapter
            }
            PRODUCT_SORT_PRICE_ASC -> {
                priceAscAdapter.setProducts(products)
                binding.priceAscProductRecyclerViewHome.layoutManager =
                    LinearLayoutManager(requireContext(), HORIZONTAL, false)
                binding.priceAscProductRecyclerViewHome.adapter = priceAscAdapter
            }
            PRODUCT_SORT_PRICE_DESC -> {
                priceDescAdapter.setProducts(products)
                binding.priceDescProductRecyclerViewHome.layoutManager =
                    LinearLayoutManager(requireContext(), HORIZONTAL, false)
                binding.priceDescProductRecyclerViewHome.adapter = priceDescAdapter
            }
        }
    }

    private fun initializeBannerSlider(
        banners: List<Banner>,
        height: Float,
    ) {
        //create an instance of BannerSliderAdapter
        val bannerSliderAdapter = BannerSliderAdapter(this, banners)
        //set BannerSliderAdapter for own viewPager
        binding.bannerSliderViewPagerHome.adapter = bannerSliderAdapter
        calculateBannerSliderHeight(height)
        //set slider indicator for viewPager
        binding.sliderIndicatorHome.setViewPager2(binding.bannerSliderViewPagerHome)
    }

    private fun calculateBannerSliderHeight(
        height: Float,
    ) {
        //calculate the height of viewPager
        val viewPagerHeight = ((binding.bannerSliderViewPagerHome.measuredWidth
                - convertDpToPixel(32f, requireContext())) * 173) / 328
        //take layoutParams for set a new Height in viewPager
        val layoutParams = binding.bannerSliderViewPagerHome.layoutParams
        //checking height isn't 0
        //if 0, then calculate height with normal way
        if (height > 0)
            layoutParams.height = height.toInt()
        else {
            layoutParams.height = viewPagerHeight.toInt()
            //then pass that to the viewPagerHeight variable in viewModel for saving
            homeViewModel.viewPagerHeight = viewPagerHeight
        }
        //set new layoutParams for viewPager
        binding.bannerSliderViewPagerHome.layoutParams = layoutParams
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClick(product: Product) {
        //start activity and pass product to ProductDetailActivity
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_PRODUCT_DATA, product)
        })
    }
}