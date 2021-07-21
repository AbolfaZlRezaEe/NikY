package com.abproject.niky.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abproject.niky.R
import com.abproject.niky.components.imageview.ImageLoadingService
import com.abproject.niky.customview.imageview.NikyImageView
import com.abproject.niky.model.dataclass.Product
import javax.inject.Inject

class SearchListAdapter @Inject constructor(
    private val imageLoadingService: ImageLoadingService,
) : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {

    private val products = ArrayList<Product>()

    var onProductClick: ((Product) -> Unit)? = null

    fun setData(
        products: List<Product>,
    ) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun deleteProduct(
        product: Product,
        position: Int,
    ) {
        this.products.remove(product)
        notifyItemRemoved(position)
    }

    inner class SearchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productTitle: TextView =
            itemView.findViewById(R.id.favoriteProductTitleItem)
        private val productImageView: NikyImageView =
            itemView.findViewById(R.id.favoriteProductImageViewItem)
        private val clickableLayout: FrameLayout =
            itemView.findViewById(R.id.clickableLayoutForClickItemProduct)

        fun bindFavoriteProduct(
            product: Product,
        ) {
            imageLoadingService.loadImage(
                imageUrl = product.image,
                imageView = productImageView
            )
            productTitle.text = product.title

            clickableLayout.setOnClickListener {
                onProductClick?.invoke(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        return SearchListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_search_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bindFavoriteProduct(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}