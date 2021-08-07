package com.abproject.niky.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun NikyImage(
    imageUrl: String,
    modifier: Modifier,
    showProgressBarIndicator: Boolean,
    errorMessage: String
) {
    GlideImage(
        imageModel = imageUrl,
        modifier = modifier,
        loading = {
            if (showProgressBarIndicator)
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val indicator = createRef()
                    CircularProgressIndicator(
                        modifier = Modifier.constrainAs(indicator) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )
                }
        },
        failure = {
            Text(
                text = errorMessage
            )
        },
    )
}