package com.abproject.niky.view.components

import androidx.compose.runtime.Composable
import com.abproject.niky.theme.NikyLayoutDirectionProvider
import com.abproject.niky.theme.NikyTheme

@Composable
fun NikyPreview(
    darkMode: Boolean = false,
    content: @Composable () -> Unit
) {
    NikyLayoutDirectionProvider {
        NikyTheme(
            darkTheme = darkMode,
            content = content
        )
    }
}