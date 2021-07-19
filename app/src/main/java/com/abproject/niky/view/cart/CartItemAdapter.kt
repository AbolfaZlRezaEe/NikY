package com.abproject.niky.view.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abproject.niky.R
import com.abproject.niky.components.imageview.ImageLoadingService
import com.abproject.niky.customview.imageview.NikyImageView
import com.abproject.niky.model.dataclass.CartItem
import com.abproject.niky.utils.exceptionhandler.exceptions.NikyException
import com.abproject.niky.model.dataclass.PurchaseDetail
import com.abproject.niky.utils.exceptionhandler.ExceptionType
import com.abproject.niky.utils.exceptionhandler.NikyExceptionMapper
import com.abproject.niky.utils.exceptionhandler.exceptions.CartException
import com.abproject.niky.utils.other.EnglishConverter
import com.abproject.niky.utils.other.UtilFunctions.formatPrice
import com.abproject.niky.utils.other.Variables.VIEW_TYPE_CART_ITEM
import com.abproject.niky.utils.other.Variables.VIEW_TYPE_PURCHASE_DETAIL
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class CartItemAdapter @Inject constructor(
    private val imageLoadingService: ImageLoadingService,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val carts = ArrayList<CartItem>()
    private var purchaseDetail: PurchaseDetail? = null

    //all listeners that this adapter and view need.
    var onItemClick: ((CartItem) -> Unit)? = null
    var onRemoveItemClick: ((CartItem) -> Unit)? = null
    var onIncreaseButtonClick: ((CartItem) -> Unit)? = null
    var onDecreaseButtonClick: ((CartItem) -> Unit)? = null

    fun setData(
        carts: List<CartItem>,
    ) {
        this.carts.clear()
        this.carts.addAll(carts)
        notifyDataSetChanged()
    }

    //setup purchase detail variable with this method
    fun setPurchaseDetail(
        totalPrice: Long,
        payablePrice: Long,
        shippingCost: Long,
    ) {
        this.purchaseDetail = PurchaseDetail(
            totalPrice = totalPrice,
            payablePrice = payablePrice,
            shippingCost = shippingCost
        )
        notifyDataSetChanged()
    }

    fun getPurchaseDetail(): PurchaseDetail? {
        return purchaseDetail
    }

    fun removeCartItem(
        cartItem: CartItem,
    ) {
        val index = carts.indexOf(cartItem)
        if (index > -1) {
            carts.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun changeCartItemCount(
        cartItem: CartItem,
    ) {
        val index = carts.indexOf(cartItem)
        if (index > -1) {
            //when successfully, most changeCountProgressBarIsVisible be false.
            carts[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CART_ITEM) {
            CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_cart,
                    parent,
                    false
                )
            )
        } else {
            PurchaseItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_purchase_details,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder) {
            holder.bindCartItem(carts[position])
        } else if (holder is PurchaseItemViewHolder) {
            purchaseDetail?.let { detail ->
                holder.bindPurchaseItem(detail)
            }
        }
    }

    override fun getItemCount(): Int {
        return carts.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == carts.size) {
            VIEW_TYPE_PURCHASE_DETAIL
        } else
            VIEW_TYPE_CART_ITEM
    }

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productImageView: NikyImageView =
            itemView.findViewById(R.id.productImageViewItemCart)
        private val productTitle: TextView =
            itemView.findViewById(R.id.productTitleTextViewItemCart)
        private val previousPrice: TextView =
            itemView.findViewById(R.id.productPreviousPriceTextViewItemCart)
        private val currentPrice: TextView =
            itemView.findViewById(R.id.productCurrentPriceTextViewItemCart)
        private val cartItemCount: TextView =
            itemView.findViewById(R.id.cartItemCountTextViewItemCart)
        private val increaseButton: ImageView =
            itemView.findViewById(R.id.increaseButtonItemCart)
        private val decreaseButton: ImageView =
            itemView.findViewById(R.id.decreaseButtonItemCart)
        private val countProgressBar: ProgressBar =
            itemView.findViewById(R.id.countProgressBarItemCart)
        private val removeProductFromCartButton: TextView =
            itemView.findViewById(R.id.removeFromCartTextViewItemCart)

        fun bindCartItem(
            cartItem: CartItem,
        ) {
            imageLoadingService.loadImage(
                imageView = productImageView,
                imageUrl = cartItem.product.image
            )
            productTitle.text = cartItem.product.title
            previousPrice.text = EnglishConverter.convertEnglishNumberToPersianNumber(
                formatPrice(cartItem.product.currentPrice + cartItem.product.discount).toString()
            )
            previousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPrice.text = EnglishConverter.convertEnglishNumberToPersianNumber(
                formatPrice(cartItem.product.currentPrice).toString()
            )
            cartItemCount.text = cartItem.count.toString()

            countProgressBar.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.INVISIBLE
            cartItemCount.visibility =
                if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE

            increaseButton.setOnClickListener {
                /*
                if the count > 5, this functionality thrown an exception that name
                CartException. and NikyExceptionMapper handle that.
                because in the server each product count can only be 5
                 */
                if (cartItem.count < 5) {
                    changeProgressVisibility(cartItem)
                    onIncreaseButtonClick?.invoke(cartItem)
                } else {
                    EventBus.getDefault().post(NikyExceptionMapper.map(
                        CartException(
                            ExceptionType.INCREASE_CART_ITEM,
                            R.string.increaseCartItemError
                        )
                    ))
                }
            }
            /*
            if the count == 1, user can not increase product count anymore.
            and if user try for this, this functionality thrown an exception.
             */
            decreaseButton.setOnClickListener {
                if (cartItem.count > 1) {
                    changeProgressVisibility(cartItem)
                    onDecreaseButtonClick?.invoke(cartItem)
                } else {
                    EventBus.getDefault().post(NikyExceptionMapper.map(
                        CartException(
                            ExceptionType.DECREASE_CART_ITEM,
                            R.string.decreaseCartItemError
                        )
                    ))
                }
            }

            removeProductFromCartButton.setOnClickListener {
                onRemoveItemClick?.invoke(cartItem)
            }

            productImageView.setOnClickListener {
                onItemClick?.invoke(cartItem)
            }
        }

        /**
         * this functionality change the visibility of the progress bar and item
         * count in the view.
         */
        private fun changeProgressVisibility(
            cartItem: CartItem,
            mustShow: Boolean = true,
        ) {
            if (mustShow) {
                cartItem.changeCountProgressBarIsVisible = true
                countProgressBar.visibility = View.VISIBLE
                cartItemCount.visibility = View.INVISIBLE
            } else {
                countProgressBar.visibility = View.INVISIBLE
                cartItemCount.visibility = View.VISIBLE
            }
        }
    }

    class PurchaseItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val totalPrice: TextView =
            itemView.findViewById(R.id.totalPriceTextViewItemPurchase)
        private val payablePrice: TextView =
            itemView.findViewById(R.id.payablePriceTextViewItemPurchase)
        private val shippingCost: TextView =
            itemView.findViewById(R.id.shippingCostTextViewItemPurchase)

        fun bindPurchaseItem(
            purchaseDetail: PurchaseDetail,
        ) {
            totalPrice.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(
                    formatPrice(purchaseDetail.totalPrice).toString()
                )
            payablePrice.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(
                    formatPrice(purchaseDetail.payablePrice).toString()
                )
            shippingCost.text =
                EnglishConverter.convertEnglishNumberToPersianNumber(
                    formatPrice(purchaseDetail.shippingCost).toString()
                )
        }
    }
}