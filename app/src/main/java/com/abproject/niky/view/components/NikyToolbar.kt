package com.abproject.niky.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abproject.niky.R
import com.abproject.niky.theme.toolbarBackgroundColor

private val NikyToolbarHeightSize = 56.dp
private val NikyToolbarElevation = 4.dp

@Composable
fun NikyToolbar(
    toolbarTitle: String,
    backButtonVisibility: Boolean,
    onBackButtonPress: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.toolbarBackgroundColor)
            .height(NikyToolbarHeightSize),
        elevation = NikyToolbarElevation
    ) {
        if (backButtonVisibility) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                IconButton(
                    onClick = onBackButtonPress,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 16.dp,
                            bottom = 16.dp
                        )
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_24dp),
                        contentDescription = null
                    )
                }
                Text(
                    text = toolbarTitle,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 8.dp,
                            end = 16.dp
                        ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = toolbarTitle,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview
fun previewToolbar() {
    NikyPreview {
        NikyToolbar(
            toolbarTitle = "نتیجه پرداخت",
            backButtonVisibility = true
        ) {

        }
    }
}