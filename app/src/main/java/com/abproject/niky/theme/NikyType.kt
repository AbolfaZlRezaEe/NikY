package com.abproject.niky.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.abproject.niky.R

private val Shabnam = FontFamily(
    Font(R.font.primary_regular, FontWeight.Light),
    Font(R.font.primary_bold, FontWeight.Bold),
    Font(R.font.primary_extra_bold, FontWeight.ExtraBold)
)

val NikyTypography = Typography(
    h5 = TextStyle(
        fontFamily = Shabnam,
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold
    ),
    h6 = TextStyle(
        fontFamily = Shabnam,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    body1 = TextStyle(
        fontFamily = Shabnam,
        fontSize = 14.sp,
        fontWeight = FontWeight.Light
    ),
    body2 = TextStyle(
        fontFamily = Shabnam,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    ),
    button = TextStyle(
        fontFamily = Shabnam,
        fontSize = 14.sp,
        fontWeight = FontWeight.Light
    )
)