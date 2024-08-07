package com.example.wonderfulcompose.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.components.debugPlaceholder
import com.example.wonderfulcompose.components.shimmerEffect
import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.data.models.CatPresenter
import com.example.wonderfulcompose.ui.theme.gradientBrush
import com.example.wonderfulcompose.ui.theme.md_theme_dark_onSurface

@Composable
fun CatItem(isLoading: Boolean, catPresenter: CatPresenter, boxHeight: Dp, onClick: () -> Unit) {
    val gradientHeight = boxHeight * 2 / 5
    val boxHeightThreshold = 200.dp

    Box(
        Modifier
            .size(boxHeight)
            .clip(RoundedCornerShape(10))
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect(),
            model = if (!isLoading) catPresenter.avatar else "",
            contentDescription = stringResource(id = R.string.content_description_avatar),
            placeholder = debugPlaceholder(debugPreview = R.drawable.previewcat),
            contentScale = ContentScale.Crop
        )
        if(boxHeight>=boxHeightThreshold) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(gradientHeight)
                    .background(brush = gradientBrush)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = catPresenter.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = md_theme_dark_onSurface
                )
            }
        }

    }
}

@PreviewUtil
@Composable
fun ChatHelperPreview() {
    CatItem(false, catList[0], boxHeight = 200.dp) {
    }
}