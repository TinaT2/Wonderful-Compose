package com.example.wonderfulcompose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.ui.theme.linearRainbowBrush
import kotlinx.coroutines.delay

@Composable
fun GradientAnimatedLinearProgressbar(
    indicatorHeight: Dp = 30.dp,
    backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.3f),
    indicatorBrush: Brush = linearRainbowBrush,
    animationDuration: Int = 10,
    animationDelay: Int = 0,
    maxRangeToPlay: Int = 100,
    modifier: Modifier,
    downloadedPercentage: MutableState<Float>
) {

    LaunchedEffect(Unit) {
        for (i in 0 until maxRangeToPlay) {
            delay(animationDuration.toLong())
            downloadedPercentage.value += 1
        }
    }

    val animateNumber = animateFloatAsState(
        targetValue = downloadedPercentage.value,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ), label = ""
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(indicatorHeight)
    ) {
        drawRoundRect(
            color = backgroundIndicatorColor,
            cornerRadius = CornerRadius(20f)
        )
        val progress = (animateNumber.value / 100) * size.width

        drawRoundRect(
            brush = indicatorBrush,
            cornerRadius = CornerRadius(20f),
            size = Size(width = progress, height = indicatorHeight.toPx())
        )

    }
}


@PreviewUtil
@Composable
fun ProgressIndicatorPreview() {
    GradientAnimatedLinearProgressbar(
        maxRangeToPlay = 80,
        modifier = Modifier.padding(20.dp),
        downloadedPercentage = mutableStateOf(90f)
    )
}