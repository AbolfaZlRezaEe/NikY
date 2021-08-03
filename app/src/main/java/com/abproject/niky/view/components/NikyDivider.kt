package com.abproject.niky.view.components

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abproject.niky.theme.dividerColor

@Composable
fun NikyDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.dividerColor,
    thickness: Dp = 1.dp
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}