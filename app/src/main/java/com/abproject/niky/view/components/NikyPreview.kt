package com.abproject.niky.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.abproject.niky.theme.NikyTheme

@Composable
fun NikyPreview(
    darkMode: Boolean = false,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl,
    ) {
        NikyTheme(
            darkTheme = darkMode,
            content = content
        )
    }
}