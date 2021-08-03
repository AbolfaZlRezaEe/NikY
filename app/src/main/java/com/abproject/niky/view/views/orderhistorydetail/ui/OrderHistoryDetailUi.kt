package com.abproject.niky.view.views.orderhistorydetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.abproject.niky.R
import com.abproject.niky.model.dataclass.OrderHistoryItem
import com.abproject.niky.model.dataclass.OrderItem
import com.abproject.niky.theme.NikyTheme
import com.abproject.niky.theme.dividerColor
import com.abproject.niky.view.components.NikyDivider
import com.abproject.niky.view.components.NikyImage
import com.abproject.niky.view.components.NikyPreview
import com.abproject.niky.view.components.NikyToolbar

@ExperimentalCoilApi
@Composable
fun OrderHistoryDetailUi(
    darkTheme: Boolean,
    orderHistoryItem: OrderHistoryItem,
    email: String,
    onToolbarBackButtonPress: () -> Unit
) {
    NikyTheme(darkTheme = darkTheme) {

        val context = LocalContext.current
        val stateScroll = rememberScrollState()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            topBar = {
                NikyToolbar(
                    toolbarTitle = context.getString(R.string.orderDetail),
                    backButtonVisibility = true,
                    onBackButtonPress = onToolbarBackButtonPress
                )
            }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.dividerColor
                    ),
                shape = RoundedCornerShape(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(stateScroll)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.firstName),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = orderHistoryItem.firstName,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    NikyDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.lastName),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = orderHistoryItem.lastName,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    NikyDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.phoneNumber),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = orderHistoryItem.phoneNumber,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    NikyDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.email),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = email,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    NikyDivider()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                    ) {
                        Text(
                            text = context.getString(R.string.address),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Start)
                        )
                        Text(
                            text = orderHistoryItem.address,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.End)
                                .padding(
                                    top = 8.dp
                                ),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,

                            )
                    }
                    NikyDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.postalCode),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = orderHistoryItem.postalCode,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    NikyDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.paymentMethod),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = orderHistoryItem.paymentMethod,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    NikyDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.paymentStatus),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = orderHistoryItem.paymentStatus,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    NikyDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.shippingCost),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = orderHistoryItem.shippingCost.toString(),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    NikyDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.totalPrice),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = orderHistoryItem.totalPrice.toString(),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    start = 16.dp
                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(132.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(
                            items = orderHistoryItem.orderItems
                        ) { item: OrderItem ->
                            NikyImage(
                                height = 100.dp,
                                width = 100.dp,
                                url = item.product.image
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun previewOrderHistoryUi() {
    val orderHistoryItem = OrderHistoryItem(
        address = "اصفهان، خمینی شهر، خیابان امیرکبیر، کوچه 109، کوچه شهید بهرامی، اول کوچه، خونه ی اول، پلاک 126، مقابل پارکینگ",
        date = "asdljkasdnlasdnadssda",
        firstName = "abolfazl",
        id = 265,
        lastName = "Rezaei",
        orderItems = listOf(),
        payable = 150000000,
        paymentMethod = "Online",
        paymentStatus = "در انتظار پرداخت",
        phoneNumber = "09135351497",
        postalCode = "8418873993",
        shippingCost = 540000,
        totalPrice = 48000000,
        userId = 15
    )
    NikyPreview {
        OrderHistoryDetailUi(
            darkTheme = false,
            email = "AbolfazlRezaei.of@gmail.com",
            orderHistoryItem = orderHistoryItem,
            onToolbarBackButtonPress = {}
        )
    }
}