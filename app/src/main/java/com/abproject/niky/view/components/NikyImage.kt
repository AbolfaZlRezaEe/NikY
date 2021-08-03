package com.abproject.niky.view.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@ExperimentalCoilApi
@Composable
fun NikyImage(
    height: Dp,
    width: Dp,
    url: String,
    @DrawableRes defaultImage: Int = 0
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            onExecute = { _, _ -> true },
            builder = {
                crossfade(true)
                //Todo("add place Holder for All Images")
                placeholder(defaultImage)
                transformations(CircleCropTransformation())
            }
        ),
        contentDescription = null,
        modifier = Modifier.size(width, height)
    )
}