package com.example.wonderfulcompose.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.components.ROUNDED_CORNER_PERCENTAGE_IMAGE
import com.example.wonderfulcompose.components.ROUNDED_CORNER_PERCENTAGE_TEXT
import com.example.wonderfulcompose.components.debugPlaceholder
import com.example.wonderfulcompose.data.fake.catList
import com.example.wonderfulcompose.ui.theme.rainbowColorsBrush

@Composable
fun CatProfileScreen(index: Int) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf(stringResource(R.string.tab_i_am), stringResource(R.string.tab_let_s_chat))
    val icons = listOf(R.drawable.icon_little_cat, R.drawable.icon_chat)
    Column {
        PrimaryTabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(selected = state == index,
                    onClick = { state = index },
                    text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                    icon = {
                        Image(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(icons[index]),
                            contentDescription = stringResource(
                                R.string.cd_cat_profile
                            )
                        )
                    })
            }
        }
        OnTabSelected(
            state = state, modifier = Modifier.align(Alignment.CenterHorizontally), index = index
        )

    }
}

@Composable
fun OnTabSelected(state: Int, modifier: Modifier, index: Int) {
    val currentCat = catList[index]
    when (state) {
        0 -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = currentCat.avatar,
                    contentDescription = stringResource(id = R.string.cd_cat_profile),
                    placeholder = debugPlaceholder(debugPreview = R.drawable.previewcat),
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(ROUNDED_CORNER_PERCENTAGE_IMAGE)),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
                ProfileText(title = "Name",content = currentCat.name)
                ProfileText(title = "Bio",content = currentCat.bio)
            }

        }

        1 -> {
            Text(
                modifier = modifier,
                text = "Good Morning from tab2",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}

@Composable
fun ProfileText(title:String,content: String) {
    Box {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(
                    BorderStroke(4.dp, rainbowColorsBrush),
                    RoundedCornerShape(ROUNDED_CORNER_PERCENTAGE_TEXT)
                )
                .padding(20.dp),
            text = content,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = title,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(PaddingValues(start = 30.dp,top = 8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(PaddingValues(start = 2.dp, end = 2.dp)),
            style = MaterialTheme.typography.titleSmall

        )
    }

}

@PreviewUtil
@Composable
fun CatProfilePreview() {
    CatProfileScreen(0)
}