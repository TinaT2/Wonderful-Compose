package com.example.wonderfulcompose

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wonderfulcompose.data.CatPresenter
import com.example.wonderfulcompose.ui.theme.md_theme_dark_onSurface

@Composable
fun PeopleItem(personPresenter: CatPresenter) {
    val boxHeight = 200.dp
    val gradientHeight = boxHeight * 2 / 5
    Box(
        Modifier
            .size(boxHeight)
            .clip(RoundedCornerShape(10))
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = personPresenter.avatar,
            contentDescription = stringResource(id = R.string.content_description_avatar),
            placeholder = debugPlaceholder(debugPreview = R.drawable.previewcat),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(gradientHeight)
                .background(brush = gradientBrush)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = personPresenter.name,
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

@PreviewUtil
@Composable
fun ChatHelperPreview() {
    PeopleItem(catList[0])
}