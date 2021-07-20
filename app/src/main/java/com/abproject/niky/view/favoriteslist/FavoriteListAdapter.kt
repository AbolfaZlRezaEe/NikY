package com.abproject.niky.view.favoriteslist

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

class FavoriteListAdapter @Inject constructor(
    private val imageLoadingService: ImageLoadingService,
) : RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder>() {

    private val products = ArrayList<Product>()

    var onDeleteButtonClick: ((Product) -> Unit)? = null
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

    inner class FavoriteListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productTitle: TextView =
            itemView.findViewById(R.id.favoriteProductTitleItem)
        private val productImageView: NikyImageView =
            itemView.findViewById(R.id.favoriteProductImageViewItem)
        private val deleteImageView: ImageView =
            itemView.findViewById(R.id.deleteProductFromFavoritesImageViewItem)
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

            deleteImageView.setOnClickListener {
                onDeleteButtonClick?.invoke(product)
                //for getting position of item please use this instead of adapterPosition.
                deleteProduct(product, absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        return FavoriteListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorite_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bindFavoriteProduct(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}