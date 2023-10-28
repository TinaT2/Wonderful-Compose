package com.example.wonderfulcompose.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val gradientBrush = Brush.verticalGradient(
    colors = listOf(
        md_theme_dark_surfaceVariant_gradient_transparent_bottom,
        md_theme_dark_surfaceVariant_gradient_transparent_top
    )
)
val rainbowColors = listOf(
    Color(0xFF9575CD),
    Color(0xFFBA68C8),
    Color(0xFFE57373),
    Color(0xFFFFB74D),
    Color(0xFFFFF176),
    Color(0xFFAED581),
    Color(0xFF4DD0E1),
    Color(0xFF9575CD)
)
val rainbowColorsBrush = Brush.sweepGradient(rainbowColors)
val linearRainbowBrush = Brush.linearGradient(rainbowColors)