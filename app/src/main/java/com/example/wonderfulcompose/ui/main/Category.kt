package com.example.wonderfulcompose.ui.main

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.components.conditional
import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.data.fake.colorCategory
import com.example.wonderfulcompose.data.models.CatPresenter
import com.example.wonderfulcompose.data.models.ListItem
import com.example.wonderfulcompose.ui.profile.CatItem

@Composable
fun Category(currentPath: String, newPath: String, onItemClick: (ListItem) -> Unit) {
    val colorId: Int? = null
    val categoryList = if (colorId == null)
        colorCategory
    else
        catList.filter { it.colorId == colorId }
    Column {
        LazyRow(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
            item {
                Text(
                    text = "$currentPath>",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$newPath>",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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
                            .size(40.dp)
                            .background(
                                color = category.color ?: Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )

                    ){
                        if(category is CatPresenter){
                            CatItem(isLoading = false, catPresenter = category) {
                                //todo
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

@PreviewUtil
@Composable
fun CategoryPreview() {
    Category(currentPath = "", newPath = "color", {})
}