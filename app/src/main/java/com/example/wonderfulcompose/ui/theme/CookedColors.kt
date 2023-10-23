package com.example.wonderfulcompose.ui.theme

import androidx.compose.ui.graphics.Brush
import com.example.wonderfulcompose.ui.theme.md_theme_dark_surfaceVariant_gradient_transparent_bottom
import com.example.wonderfulcompose.ui.theme.md_theme_dark_surfaceVariant_gradient_transparent_top

val gradientBrush = Brush.verticalGradient(
    colors = listOf(
        md_theme_dark_surfaceVariant_gradient_transparent_bottom,
        md_theme_dark_surfaceVariant_gradient_transparent_top
    ) // Define your gradient colors
)