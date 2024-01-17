package com.example.wonderfulcompose.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
fun CatItem(
    isLoading: Boolean,
    cat: CatPresenter,
    onClick: (cat: CatPresenter) -> Unit,
    onFavoriteClick: (cat: CatPresenter) -> Unit,
    isFavorite: Boolean
) {
    val boxHeight = 200.dp
    val gradientHeight = boxHeight * 2 / 5


    Box(
        Modifier
            .size(boxHeight)
            .clip(RoundedCornerShape(10))
            .clickable {
                onClick(cat)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect(),
            model = if (!isLoading) cat.avatar else "",
            contentDescription = stringResource(id = R.string.content_description_avatar),
            placeholder = debugPlaceholder(debugPreview = R.drawable.previewcat),
            contentScale = ContentScale.Crop
        )
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite
            else Icons.Rounded.FavoriteBorder,
            contentDescription = "favorite",
            tint = Color.Gray,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopEnd)
                .clickable { onFavoriteClick(cat) },
        )
        Box(
            Modifier
                .fillMaxWidth()
                .height(gradientHeight)
                .background(brush = gradientBrush)
                .align(Alignment.BottomCenter)
        ) {

            Text(
                text = cat.name,
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
    CatItem(
        isLoading = false,
        cat = catList[0],
        onClick = {},
        onFavoriteClick = {},
        isFavorite = false
    )
}