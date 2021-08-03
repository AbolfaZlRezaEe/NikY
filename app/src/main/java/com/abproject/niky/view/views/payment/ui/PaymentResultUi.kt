package com.abproject.niky.view.views.payment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abproject.niky.R
import com.abproject.niky.theme.dividerColor
import com.abproject.niky.utils.other.EnglishConverter
import com.abproject.niky.utils.other.UtilFunctions.formatPrice
import com.abproject.niky.view.components.NikyDivider
import com.abproject.niky.view.components.NikyPreview
import com.abproject.niky.view.components.NikyToolbar

@Composable
fun PaymentResultUi(
    purchaseStatus: String,
    orderStatus: String,
    orderPrice: Int,
    onPressReturnHome: () -> Unit,
    onPressOrderHistory: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        topBar = {
            NikyToolbar(
                toolbarTitle = context.getString(R.string.paymentResultToolbarTitle),
                backButtonVisibility = false,
                onBackButtonPress = {}
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(bottom = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.dividerColor
                    ),
                shape = RoundedCornerShape(2.dp),
            ) {
                Column {
                    Text(
                        text = purchaseStatus,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(
                                top = 24.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.purchaseStatus),
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = orderStatus,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(end = 16.dp)
                        )
                    }
                    NikyDivider(
                        modifier = Modifier
                            .padding(top = 12.dp),
                    )
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 12.dp,
                                bottom = 12.dp
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = context.getString(R.string.amount),
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = EnglishConverter.convertEnglishNumberToPersianNumber(
                                formatPrice(orderPrice).toString()
                            ),
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(end = 16.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = onPressReturnHome,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = context.getString(R.string.returnToHome),
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedButton(
                    onClick = onPressOrderHistory,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = context.getString(R.string.orderHistory),
                        style = MaterialTheme.typography.button,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview()
@Composable
fun previewPaymentUi() {
    NikyPreview {
        PaymentResultUi(
            purchaseStatus = "پرداخت شما با موفقیت انجام گردید",
            orderStatus = "در انتظار پرداخت",
            orderPrice = 16000000,
            onPressOrderHistory = {},
            onPressReturnHome = {}
        )
    }
}