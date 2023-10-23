package com.example.wonderfulcompose.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.R

@Composable
fun CatProfileScreen() {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf(stringResource(R.string.tab_i_am), stringResource(R.string.tab_let_s_chat))
    val icons = listOf(R.drawable.icon_little_cat, R.drawable.icon_chat)
    Column {
        PrimaryTabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                    icon = {
                        Image(
                            painter = painterResource(icons[index]),
                            contentDescription = stringResource(
                                R.string.cd_cat_profile
                            )
                        )
                    }
                )
            }
        }
        OnTabSelected(state = state, modifier = Modifier.align(Alignment.CenterHorizontally))

    }
}

@Composable
fun OnTabSelected(state: Int, modifier: Modifier) {
    when (state) {
        0 -> {
            Text(
                modifier = modifier,
                text = "Hello from tab1",
                style = MaterialTheme.typography.bodyLarge
            )
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

@PreviewUtil
@Composable
fun CatProfilePreview() {
    CatProfileScreen()
}