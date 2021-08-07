package com.abproject.niky.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val NikyLightPalette = lightColors(
    primary = blue,
    onPrimary = Color.White,
    secondary = black,
    onSecondary = Color.White,
    surface = Color.White,
    onSurface = black,
    background = Color.White,
    onBackground = black
)

val Colors.dividerColor: Color
    @Composable
    get() = gray

val Colors.progressBarIndicatorBackground: Color
    @Composable
    get() = black

val Colors.toolbarBackgroundColor: Color
    @Composable
    get() = Color.White

@Composable
fun NikyTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    //Todo("for use dark theme, use a new object call colors for MaterialTheme!")
    MaterialTheme(
        colors = NikyLightPalette,
        typography = NikyTypography,
        shapes = NikyShape,
        content = content
    )
}

@Composable
fun NikyLayoutDirectionProvider(
    layoutDirection: LayoutDirection = LayoutDirection.Rtl,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides layoutDirection,
        content = content
    )
}