package com.abproject.niky.view.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abproject.niky.R
import com.abproject.niky.components.imageview.ImageLoadingService
import com.abproject.niky.customview.NikyImageView
import com.abproject.niky.model.model.Product
import com.abproject.niky.utils.EnglishConverter
import com.abproject.niky.utils.UtilFunctions.formatPrice
import com.abproject.niky.utils.implementSpringAnimationTrait
import javax.inject.Inject

class ProductHomeAdapter @Inject constructor(
    private val imageLoadingService: ImageLoadingService,
) : RecyclerView.Adapter<ProductHomeAdapter.ProductHomeViewHolder>() {

    private val products = ArrayList<Product>()

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
                EnglishConverter.convertEnglishNumberToPersianNumber(formatPrice(product.price).toString())
            productCurrentPrice.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(formatPrice(product.previous_price).toString())
            imageLoadingService.loadImage(productImageView, product.image)
            productPreviousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            productFavorite.setOnClickListener {

            }
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHomeViewHolder {
        return ProductHomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHomeViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}