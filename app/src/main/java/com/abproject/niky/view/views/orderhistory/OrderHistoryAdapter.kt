package com.abproject.niky.view.views.orderhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abproject.niky.R
import com.abproject.niky.customview.imageview.NikyImageView
import com.abproject.niky.model.dataclass.OrderHistoryItem
import com.abproject.niky.utils.other.EnglishConverter
import com.abproject.niky.utils.other.UtilFunctions.convertDpToPixel
import com.abproject.niky.utils.other.UtilFunctions.formatPrice
import com.google.android.material.button.MaterialButton

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    var context: Context? = null

    private val orderHistories = ArrayList<OrderHistoryItem>()

    var onOrderDetailButtonClick:
            ((orderHistoryItem: OrderHistoryItem) -> Unit)? = null

    fun setData(orderHistories: List<OrderHistoryItem>) {
        this.orderHistories.clear()
        this.orderHistories.addAll(orderHistories)
        notifyDataSetChanged()
    }

    inner class OrderHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var layoutParams: LinearLayout.LayoutParams
        private val orderId: TextView =
            itemView.findViewById(R.id.orderIdTextViewItemOrderHistory)
        private val orderAmount: TextView =
            itemView.findViewById(R.id.orderAmountTextViewItemOrder)
        private val orderProduct: LinearLayout =
            itemView.findViewById(R.id.orderProductsLinearLayout)
        private val orderDetailButton: MaterialButton =
            itemView.findViewById(R.id.orderDetailMaterialButtonOrderHistory)

        init {
            processSizeOfOrderImage()
        }

        fun bindOrderHistory(
            orderHistoryItem: OrderHistoryItem,
        ) {
            orderId.text = EnglishConverter.convertEnglishNumberToPersianNumber(
                orderHistoryItem.id.toString()
            )
            orderAmount.text = EnglishConverter.convertEnglishNumberToPersianNumber(
                formatPrice(orderHistoryItem.payable).toString()
            )
            orderProduct.removeAllViews()
            orderHistoryItem.orderItems.forEach { orderItem ->
                val imageView = NikyImageView(context)
                imageView.layoutParams = layoutParams
                imageView.setImageURI(orderItem.product.image)
                orderProduct.addView(imageView)
            }
            orderDetailButton.setOnClickListener {
                onOrderDetailButtonClick?.invoke(orderHistoryItem)
            }
        }

        private fun processSizeOfOrderImage() {
            val size = convertDpToPixel(100f, context).toInt()
            val margin = convertDpToPixel(8f, context).toInt()
            layoutParams = LinearLayout.LayoutParams(size, size)
            layoutParams.setMargins(margin, 0, margin, 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        return OrderHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_order_history,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        holder.bindOrderHistory(orderHistories[position])
    }

    override fun getItemCount(): Int {
        return orderHistories.size
    }

}