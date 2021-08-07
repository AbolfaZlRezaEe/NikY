package com.abproject.niky.view.views.orderhistorydetail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.model.objects.UserContainer
import com.abproject.niky.theme.NikyLayoutDirectionProvider
import com.abproject.niky.theme.NikyTheme
import com.abproject.niky.view.views.orderhistorydetail.ui.OrderHistoryDetailUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryDetailActivity : NikyActivity() {

    private val orderHistoryDetailViewModel: OrderHistoryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val response by orderHistoryDetailViewModel.getOrderHistoryItem.observeAsState()

            NikyLayoutDirectionProvider {
                NikyTheme(darkTheme = false) {
                    response?.let { orderHistoryItem ->
                        OrderHistoryDetailUi(
                            orderHistoryItem = orderHistoryItem,
                            email = UserContainer.email!!,
                            onToolbarBackButtonPress = { finish() }
                        )
                    }
                }
            }
        }
    }
}