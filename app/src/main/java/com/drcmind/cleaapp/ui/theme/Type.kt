package com.drcmind.cleaapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// In a real production app, we would download the Plus Jakarta Sans .ttf files
// into res/font and define the FontFamily here. For now we use the default Sans-Serif
// to match the weights and sizes specified in the design system.
val PlusJakartaSans = FontFamily.Default

val Typography = Typography(
    displayMedium = TextStyle( // Used as headline-xl
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = (-0.02).sp
    ),
    headlineLarge = TextStyle( // Used as headline-lg
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Bold, // 700
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.01).sp
    ),
    headlineMedium = TextStyle( // Used as headline-md
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.SemiBold, // 600
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    bodyLarge = TextStyle( // Used as body-lg
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 18.sp,
        lineHeight = 28.sp
    ),
    bodyMedium = TextStyle( // Used as body-md
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Normal, // 400
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelMedium = TextStyle( // Used as label-md
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.SemiBold, // 600
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.05.sp
    ),
    bodySmall = TextStyle( // Used as caption
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Medium, // 500
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)