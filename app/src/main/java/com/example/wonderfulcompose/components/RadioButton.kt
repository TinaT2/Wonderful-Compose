package com.example.wonderfulcompose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BasicRadioButton(
    itemTitle: String,
    isItemSelected: Boolean,
    onItemSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isItemSelected,
            onClick = { onItemSelected(itemTitle) }
        )
        Text(
            text = itemTitle,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}