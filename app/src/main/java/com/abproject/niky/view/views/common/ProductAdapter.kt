package com.abproject.niky.view.views.common

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abproject.niky.R
import com.abproject.niky.components.imageview.ImageLoadingService
import com.abproject.niky.customview.imageview.NikyImageView
import com.abproject.niky.model.dataclass.Product
import com.abproject.niky.utils.other.EnglishConverter
import com.abproject.niky.utils.other.UtilFunctions.formatPrice
import com.abproject.niky.utils.other.Variables.PRODUCT_VIEW_TYPE_GRID
import com.abproject.niky.utils.other.Variables.PRODUCT_VIEW_TYPE_LARGE
import com.abproject.niky.utils.other.Variables.PRODUCT_VIEW_TYPE_ROUNDED
import com.abproject.niky.utils.other.implementSpringAnimationTrait
import javax.inject.Inject

class ProductAdapter @Inject constructor(
    private val imageLoadingService: ImageLoadingService,
) : RecyclerView.Adapter<ProductAdapter.ProductHomeViewHolder>() {

    private val products = ArrayList<Product>()

    //for handling which view type show
    private var viewType: Int = PRODUCT_VIEW_TYPE_ROUNDED

    //for on click listeners on products
    var productListener: ProductListener? = null
    var onFavoriteButtonClick: ((product: Product) -> Unit)? = null

    /**
     * this function take a viewType as input
     * and set that viewType in Adapter.
     */
    fun setViewType(
        viewType: Int,
    ) {
        this.viewType = viewType
        notifyDataSetChanged()
    }

    //get viewType
    fun getViewType(): Int {
        return viewType
    }

    fun setProducts(
        products: List<Product>,
    ) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    inner class ProductHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productTitle: TextView =
            itemView.findViewById(R.id.productTitleTextViewItem)
        private val productCurrentPrice: TextView =
            itemView.findViewById(R.id.productCurrentPriceTextViewItem)
        private val productPreviousPrice: TextView =
            itemView.findViewById(R.id.productPreviousPriceTextViewItem)
        private val productImageView: NikyImageView =
            itemView.findViewById(R.id.productImageViewItem)
        private val productFavorite: ImageView =
            itemView.findViewById(R.id.productFavoriteImageViewItem)

        fun bindProduct(
            product: Product,
        ) {
            productTitle.text = product.title

            productPreviousPrice.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(formatPrice(product.currentPrice).toString())

            productCurrentPrice.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(formatPrice(product.previousePrice).toString())
            imageLoadingService.loadImage(productImageView, product.image)

            productPreviousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            productFavorite.setOnClickListener {
                productListener?.onFavoriteButtonClick(product)
                product.isFavorite = !product.isFavorite
                notifyItemChanged(absoluteAdapterPosition)
            }

            itemView.implementSpringAnimationTrait()

            itemView.setOnClickListener {
                productListener?.onProductClick(product)
            }

            if (product.isFavorite)
                productFavorite.setImageResource(R.drawable.ic_favorites_fill_16dp)
            else
                productFavorite.setImageResource(R.drawable.ic_favorites_16dp)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHomeViewHolder {
        return ProductHomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                getLayoutResId(viewType),
                parent,
                false
            )
        )
    }

    /**
     * getLayoutResId take viewType variable in onCreateViewHolder and
     * check it, then return a resource layout that contain a sort in
     * product list.
     * Note: if viewType isn't valid, this function throwing an Exception!
     */
    private fun getLayoutResId(
        viewType: Int,
    ): Int {
        return when (viewType) {
            PRODUCT_VIEW_TYPE_LARGE -> R.layout.item_product_large
            PRODUCT_VIEW_TYPE_ROUNDED -> R.layout.item_product_rounded
            PRODUCT_VIEW_TYPE_GRID -> R.layout.item_product_grid
            else -> throw IllegalStateException("ProductAdapter (debug)-> entered View Type isn't valid!")
        }
    }

    override fun onBindViewHolder(holder: ProductHomeViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface ProductListener {
        fun onProductClick(product: Product)
        fun onFavoriteButtonClick(product: Product)
    }
}