package com.abproject.niky.view.productdetail

import com.abproject.niky.R
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.components.imageview.ImageLoadingService
import com.abproject.niky.customview.scrollview.ObservableScrollViewCallbacks
import com.abproject.niky.customview.scrollview.ScrollState
import com.abproject.niky.databinding.ActivityProductDetailBinding
import com.abproject.niky.model.dataclass.Comment
import com.abproject.niky.model.dataclass.Product
import com.abproject.niky.utils.other.EnglishConverter
import com.abproject.niky.utils.other.UtilFunctions.formatPrice
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_ID_DATA
import com.abproject.niky.utils.other.Variables.SHOW_THREE_COMMENTS
import com.abproject.niky.utils.rxjava.NikyCompletableObserver
import com.abproject.niky.view.comment.CommentActivity
import com.abproject.niky.view.comment.CommentAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailActivity : NikyActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val productDetailViewModel: ProductDetailViewModel by viewModels()
    private val commentAdapter = CommentAdapter()

    @Inject
    lateinit var imageLoadingService: ImageLoadingService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeningToObservers()
    }

    private fun listeningToObservers() {
        productDetailViewModel.getProduct.observe(this) { product ->
            setupUi(product)
        }

        productDetailViewModel.getComments.observe(this) { response ->
            setupCommentSection(response)

        }

        productDetailViewModel.progressbarStatus.observe(this) { show ->
            showProgressbar(show)
        }
    }

    private fun setupUi(
        product: Product,
    ) {
        imageLoadingService.loadImage(
            binding.productImageViewProductDetail,
            product.image
        )
        binding.productTitleTextViewProductDetail.text = product.title

        binding.productPreviousPriceTextViewProductDetail.text =
            EnglishConverter.convertEnglishNumberToPersianNumber(formatPrice(product.previousPrice).toString())

        binding.productPreviousPriceTextViewProductDetail.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        binding.productCurrentPriceTextViewProductDetail.text =
            EnglishConverter.convertEnglishNumberToPersianNumber(formatPrice(product.currentPrice).toString())

        binding.toolbarTitleTextViewProductDetail.text = product.title

        initializeProductImageViewAnimation()

        binding.backImageViewProductDetail.setOnClickListener {
            this.finish()
        }

        //initialize add to cart button clicked.
        binding.addToCartExtendedFabProductDetail.setOnClickListener {
            productDetailViewModel.addProductToCart()
                .subscribe(object :
                    NikyCompletableObserver(productDetailViewModel.compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.addToCartSuccessfully))
                    }
                })
        }
    }

    private fun setupCommentSection(
        comments: List<Comment>,
    ) {
        commentAdapter.adapterFlag = SHOW_THREE_COMMENTS
        commentAdapter.setData(comments)
        binding.commentsRecyclerViewProductDetail.layoutManager = LinearLayoutManager(
            this,
            VERTICAL,
            false
        )
        binding.commentsRecyclerViewProductDetail.adapter = commentAdapter

        if (comments.size > 3) {
            binding.viewAllCommentsMaterialButtonProductDetail.visibility = View.VISIBLE
            binding.viewAllCommentsMaterialButtonProductDetail.setOnClickListener {
                startActivity(Intent(this, CommentActivity::class.java).apply {
                    putExtra(EXTRA_KEY_PRODUCT_ID_DATA,
                        productDetailViewModel.getProduct.value!!.id)
                })
            }
        }
    }

    /**
     * initializeProductImageViewAnimation generate for parallax animation.
     */
    private fun initializeProductImageViewAnimation() {
        binding.productImageViewProductDetail.post {
            val productImageViewHeight = binding.productImageViewProductDetail.height
            val toolbar = binding.toolbarProductDetail

            binding.observableScrollViewProductDetail.addScrollViewCallbacks(object :
                ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean,
                ) {
                    toolbar.alpha = scrollY.toFloat() / productImageViewHeight.toFloat()
                    binding.productImageViewProductDetail.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }
            })
        }
    }
}