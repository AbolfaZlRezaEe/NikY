package com.abproject.niky.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.abproject.niky.base.NikyFragment
import com.abproject.niky.databinding.FragmentHomeBinding
import com.abproject.niky.model.model.Banner
import com.abproject.niky.model.model.Product
import com.abproject.niky.utils.UtilFunctions.convertDpToPixel
import com.abproject.niky.utils.Variables.EXTRA_KEY_PRODUCT_DATA
import com.abproject.niky.utils.Variables.PRODUCT_SORT_LATEST
import com.abproject.niky.utils.Variables.PRODUCT_SORT_POPULAR
import com.abproject.niky.utils.Variables.PRODUCT_SORT_PRICE_ASC
import com.abproject.niky.utils.Variables.PRODUCT_SORT_PRICE_DESC
import com.abproject.niky.view.home.banner.BannerSliderAdapter
import com.abproject.niky.view.productdetail.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : NikyFragment(), ProductHomeAdapter.ProductListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    /**
     * it's so bad. but i have no choice.
     * create a 4 instance of ProductHomeAdapter
     * for each of the lists.
     */
    @Inject
    lateinit var popularAdapter: ProductHomeAdapter

    @Inject
    lateinit var latestAdapter: ProductHomeAdapter

    @Inject
    lateinit var priceAscAdapter: ProductHomeAdapter

    @Inject
    lateinit var priceDescAdapter: ProductHomeAdapter

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
    }

    //listening to all observers that this class needs.
    private fun listeningToObservers() {
        homeViewModel.progressbarStatus.observe(viewLifecycleOwner) { status ->
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
            initializeBannerSlider(response)
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
    ) {
        //create an instance of BannerSliderAdapter
        val bannerSliderAdapter = BannerSliderAdapter(this, banners)
        //set BannerSliderAdapter for own viewPager
        binding.bannerSliderViewPagerHome.adapter = bannerSliderAdapter
        calculateBannerSliderHeight()
        //set slider indicator for viewPager
        binding.sliderIndicatorHome.setViewPager2(binding.bannerSliderViewPagerHome)
    }

    private fun calculateBannerSliderHeight() {
        //calculate the height of viewPager
        val viewPagerHeight = ((binding.bannerSliderViewPagerHome.measuredWidth
                - convertDpToPixel(32f, requireContext())) * 173) / 328
        //take layoutParams for set a new Height in viewPager
        val layoutParams = binding.bannerSliderViewPagerHome.layoutParams
        layoutParams.height = viewPagerHeight.toInt()
        //set new layoutParams for viewPager
        binding.bannerSliderViewPagerHome.layoutParams = layoutParams
    }


    override fun onResume() {
        super.onResume()
        //for calculate again banner slider height
        calculateBannerSliderHeight()
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