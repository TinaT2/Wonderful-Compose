package com.example.wonderfulcompose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.ui.theme.linearRainbowBrush
import kotlinx.coroutines.delay

@Composable
fun GradientProgressbar1(
    indicatorHeight: Dp = 30.dp,
    backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.3f),
    indicatorPadding: Dp = 24.dp,
    animationDuration: Int = 100,
    animationDelay: Int = 0,
    maxRangeToPlay: Int = 100
) {
    val downloadedPercentage = remember { mutableFloatStateOf(0f) }
    LaunchedEffect(Unit) {
        for (i in 0 until maxRangeToPlay) {
            delay(animationDuration.toLong())
            downloadedPercentage.floatValue += 1
        }
    }

    val animateNumber = animateFloatAsState(
        targetValue = downloadedPercentage.floatValue,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ), label = ""
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(indicatorHeight)
            .padding(start = indicatorPadding, end = indicatorPadding)
    ) {

        drawRoundRect(
            color = backgroundIndicatorColor,
            cornerRadius = CornerRadius(20f)
        )

        // Convert the downloaded percentage into progress (width of foreground indicator)
        val progress =
            (animateNumber.value / 100) * size.width // size.width returns the width of the canvas

        // Foreground indicator
        drawRoundRect(
            brush = linearRainbowBrush,
            cornerRadius = CornerRadius(20f),
            size = Size(width = progress, height = indicatorHeight.toPx())
        )

    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = downloadedPercentage.floatValue.toInt().toString() + "%",
        style = MaterialTheme.typography.bodyMedium
    )

}