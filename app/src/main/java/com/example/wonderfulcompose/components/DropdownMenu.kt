package com.example.wonderfulcompose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.ui.theme.md_theme_light_primary

@Composable
fun <T> DropdownMenu(
    modifier: Modifier = Modifier,
    label: String,
    items: List<T>,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: T) -> Unit,
    selectedItemToString: (T) -> String = { it.toString() },
) {
    val expanded = remember { mutableStateOf(false) }

    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        OutlinedTextField(
            label = { Text(label) },
            value = items.getOrNull(selectedIndex)?.let { selectedItemToString(it) } ?: "",
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            onValueChange = { },
            readOnly = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = md_theme_light_primary,
                unfocusedIndicatorColor = md_theme_light_primary
            ),
            trailingIcon = {
                val icon =
                    if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
                Icon(icon, "")
            }
        )

        // Transparent clickable surface on top of OutlinedTextField
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable { expanded.value = !expanded.value },
            color = Color.Transparent,
        ) {}
    }

    if (expanded.value)
        DropdownMenuItemList(
            expanded = expanded,
            selectedIndex = selectedIndex,
            items = items,
        ) { index, item -> onItemSelected(index, item) }
}

@Composable
fun DropdownMenuItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(modifier = Modifier
        .clickable { onClick() }
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = if (selected)
                MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun <T> DropdownMenuItemList(
    expanded: MutableState<Boolean>,
    selectedIndex: Int,
    items: List<T>,
    onItemSelected: (index: Int, item: T) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 12.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(300.dp)

    ) {
        val listState = rememberLazyListState()
        if (selectedIndex > -1) {
            LaunchedEffect("ScrollToSelected") {
                listState.scrollToItem(index = selectedIndex)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            itemsIndexed(items) { index, item ->
                val selectedItem = index == selectedIndex
                DropdownMenuItem(text = item.toString(), selected = selectedItem,
                ) {
                    onItemSelected(index, item)
                    expanded.value = false
                }

                if (index < items.lastIndex)
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}
