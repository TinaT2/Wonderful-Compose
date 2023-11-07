package com.example.wonderfulcompose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.wonderfulcompose.R


@Composable
fun BasicDialogBox(
    dialogTitle: String,
    dialogText: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = positiveButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = negativeButtonText)
            }
        }
    )
}

@PreviewUtil
@Composable
fun BasicDialogBoxPreview() {
    BasicDialogBox(
        dialogTitle = stringResource(id = R.string.unsaved_changes_dialog_title),
        dialogText = stringResource(id = R.string.unsaved_changes_dialog_text),
        positiveButtonText = stringResource(id = R.string.dialog_button_yes),
        negativeButtonText = stringResource(id = R.string.dialog_button_no),
        onConfirmation = { }) {
    }
}