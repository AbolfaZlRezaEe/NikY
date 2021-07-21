package com.abproject.niky.view.orderhistory

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityOrderHistoryBinding
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_ORDER_HISTORY_ITEM
import com.abproject.niky.view.main.MainActivity
import com.abproject.niky.view.orderhistorydetail.OrderHistoryDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OrderHistoryActivity : NikyActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    private val orderHistoryAdapter = OrderHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        listeningToTheObservers()
    }

    private fun initializeViews() {
        orderHistoryAdapter.context = this

        orderHistoryAdapter.onOrderDetailButtonClick = { orderHistoryItem ->
            startActivity(Intent(this, OrderHistoryDetailActivity::class.java).apply {
                putExtra(EXTRA_KEY_ORDER_HISTORY_ITEM, orderHistoryItem)
            })
        }
        binding.recyclerViewOrderHistory.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerViewOrderHistory.adapter = orderHistoryAdapter

        binding.toolbarOrderHistory.onBackButtonClickListener = View.OnClickListener {
            processBackStack()
        }
    }

    /**
     * this function check the activity backstack and then
     * if this activity is the last activity on back stack
     * then start main activity again for UX..
     * Why -> because in the PaymentResultActivity we finish
     * the MainActivity and if user click the orderHistory
     * button and after that click the back button we have
     * not activity for show to the user.
     */
    private fun processBackStack() {
        if (isTaskRoot) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else
            finish()
    }

    private fun listeningToTheObservers() {
        orderHistoryViewModel.progressbarStatusLiveData.observe(this) { status ->
            showProgressbar(status)
        }
        orderHistoryViewModel.getOrderHistoryItemsLiveData.observe(this) { orderHistoryItems ->
            orderHistoryAdapter.setData(orderHistoryItems)
        }
        orderHistoryViewModel.emptyStateStatusLiveData.observe(this) { emptyState ->
            //create empty state view
            val emptyStateView = showEmptyState(R.layout.view_default_empty_state, R.id.emptyView)
            if (emptyState.mustShowEmptyState) {
                emptyStateView?.let { view ->
                    //set values if mustShowEmptyState is true
                    view.findViewById<TextView>(R.id.emptyStateMessageTextView).text =
                        getString(emptyState.emptyStateMessage)
                }
            } else {
                emptyStateView?.visibility = View.INVISIBLE
            }
        }
    }

    override fun onBackPressed() {
        processBackStack()
    }
}