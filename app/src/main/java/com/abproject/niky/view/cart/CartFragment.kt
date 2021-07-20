package com.abproject.niky.view.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.abproject.niky.R
import com.abproject.niky.base.NikyFragment
import com.abproject.niky.databinding.FragmentCartBinding
import com.abproject.niky.utils.other.Variables.DECREASE_CART_ITEM
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_DATA
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PURCHASE_DETAIL
import com.abproject.niky.utils.other.Variables.INCREASE_CART_ITEM
import com.abproject.niky.utils.rxjava.NikyCompletableObserver
import com.abproject.niky.view.productdetail.ProductDetailActivity
import com.abproject.niky.view.shipping.ShippingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : NikyFragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()

    @Inject
    lateinit var cartItemAdapter: CartItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeningToTheObservers()
        initializeRecyclerView()
        initializeViews()
        cartViewModel.getCartItems()
    }

    private fun initializeViews() {
        binding.payMaterialButtonCart.setOnClickListener {
            startActivity(Intent(requireContext(), ShippingActivity::class.java).apply {
                // TODO: 7/19/2021 force close because getPurchaseDetail is null
                putExtra(EXTRA_KEY_PURCHASE_DETAIL, cartItemAdapter.getPurchaseDetail())
            })
            requireActivity().finish()
        }
    }

    private fun listeningToTheObservers() {
        cartViewModel.emptyStateStatusLiveData.observe(viewLifecycleOwner) { emptyState ->
            //create empty state view
            val emptyStateView =
                showEmptyState(R.layout.view_cart_empty_state, R.id.rootViewCartEmptyState)
            if (emptyState.mustShowEmptyState) {
                emptyStateView?.let { view ->
                    //set values if mustShowEmptyState is true
                    view.findViewById<TextView>(R.id.messageCartEmptyState).text =
                        getString(emptyState.emptyStateMessage)
                }
            } else
                emptyStateView?.visibility = View.GONE
        }

        cartViewModel.progressbarStatusLiveData.observe(viewLifecycleOwner) { show ->
            showProgressbar(show)
        }

        cartViewModel.cartItemsLiveData.observe(viewLifecycleOwner) { cartItems ->
            //set cart items for adapter
            cartItemAdapter.setData(cartItems)
        }

        cartViewModel.purchaseDetailLiveData.observe(viewLifecycleOwner) { purchaseDetail ->
            //setup purchaseDetail for adapter
            cartItemAdapter.setPurchaseDetail(
                totalPrice = purchaseDetail.totalPrice,
                payablePrice = purchaseDetail.payablePrice,
                shippingCost = purchaseDetail.shippingCost
            )
        }
    }

    private fun initializeRecyclerView() {
        binding.cartRecyclerViewCart.layoutManager =
            LinearLayoutManager(
                requireContext(),
                VERTICAL,
                false
            )
        binding.cartRecyclerViewCart.adapter = cartItemAdapter

        cartItemAdapter.onItemClick = { cartItem ->
            //start activity and pass product to ProductDetailActivity
            startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_KEY_PRODUCT_DATA, cartItem.product)
            })
        }

        cartItemAdapter.onRemoveItemClick = { cartItem ->
            cartViewModel.removeProductFromCart(cartItem)
                .subscribe(object : NikyCompletableObserver(cartViewModel.compositeDisposable) {
                    override fun onComplete() {
                        cartItemAdapter.removeCartItem(cartItem)
                    }
                })
        }

        cartItemAdapter.onIncreaseButtonClick = { cartItem ->
            cartViewModel.changeCartItemCount(cartItem, INCREASE_CART_ITEM)
                .subscribe(object : NikyCompletableObserver(cartViewModel.compositeDisposable) {
                    override fun onComplete() {
                        cartItemAdapter.changeCartItemCount(cartItem)
                    }
                })
        }

        cartItemAdapter.onDecreaseButtonClick = { cartItem ->
            cartViewModel.changeCartItemCount(cartItem, DECREASE_CART_ITEM)
                .subscribe(object : NikyCompletableObserver(cartViewModel.compositeDisposable) {
                    override fun onComplete() {
                        cartItemAdapter.changeCartItemCount(cartItem)
                    }
                })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}