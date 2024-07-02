package com.example.wonderfulcompose.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.data.fake.colorCategory
import com.example.wonderfulcompose.data.models.CatPresenter
import com.example.wonderfulcompose.data.models.ListItem
import com.example.wonderfulcompose.ui.profile.CatItem

typealias Path = String

@Composable
fun Category(
    pathList: List<Path>,
    colorId: Int? = null,
    onItemClick: (ListItem) -> Unit,
    onPathClicked: (Path) -> Unit
) {
    val categoryList = if (colorId == null)
        colorCategory
    else
        catList.filter { it.colorId == colorId }

    val boxHeight = 60.dp
    Column {
        LazyRow(modifier = Modifier.padding(start = 8.dp, top = 4.dp)) {
            items(pathList) { path ->
                path.toName().apply {
                    when (this) {
                        is String -> {
                            Text(
                                text = "${path.toName()}>",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier
                                    .clickable { onPathClicked(path) }
                                    .padding(top = 6.dp)
                            )
                        }

                        is ImageVector -> {
                            Icon(
                                imageVector = this,
                                contentDescription = stringResource(id = R.string.home),
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier
                                    .clickable { onPathClicked(path) }
                            )
                            Text(
                                text = ">",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }


            }
        }
        LazyColumn {
            items(categoryList) { category ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(category) }
                    .padding(top = 8.dp, start = 8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(boxHeight)
                            .background(
                                color = category.color ?: Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )

                    ) {
                        if (category is CatPresenter) {
                            CatItem(
                                isLoading = false,
                                catPresenter = category,
                                boxHeight = boxHeight
                            ) {
                                onItemClick(category)
                            }
                        }
                    }
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 4.dp),
                        text = category.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Suppress("IMPLICIT_CAST_TO_ANY")
@Composable
fun Path.toName() = when (this) {
    Main.route -> Icons.Default.Home
    ColorCategory.route -> stringResource(R.string.colors)
    else -> this
}


@PreviewUtil
@Composable
fun CategoryPreview() {
    Category(pathList = listOf("color", "red"), onItemClick = {}, onPathClicked = {})
}