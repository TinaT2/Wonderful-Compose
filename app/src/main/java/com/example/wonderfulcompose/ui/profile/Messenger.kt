package com.example.wonderfulcompose.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.components.ROUNDED_CORNER_PERCENTAGE_CHAT
import com.example.wonderfulcompose.components.chatflexbox.ChatFlexBoxLayout
import com.example.wonderfulcompose.data.fake.messageList
import com.example.wonderfulcompose.data.models.MessagePresenter
import com.example.wonderfulcompose.data.models.isMessageMine

@Composable
fun MessageList(messageList: List<MessagePresenter>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(messageList) { message ->
                TextMessageItem(message)
            }
        }
        var text = remember { mutableStateOf("Type Something...") }

        MessengerInput(text = text, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun MessengerInput(text: MutableState<String>, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), contentAlignment = Alignment.BottomCenter
    ) {
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
        )

        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(8.dp),
            imageVector = Icons.AutoMirrored.Outlined.Send,
            contentDescription = stringResource(R.string.cd_send)
        )
    }
}

@Composable
fun TextMessageItem(messagePresenter: MessagePresenter) {

    messagePresenter.apply {
        val bubbleColor = if (isMessageMine()) MaterialTheme.colorScheme.inversePrimary
        else MaterialTheme.colorScheme.tertiaryContainer
        val textColor = if (isMessageMine()) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onTertiaryContainer
        val chatMaxPadding = 54.dp
        val bubbleMinSize = 180.dp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isMessageMine()) Arrangement.End else Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .defaultMinSize(bubbleMinSize)
                    .padding(8.dp)
                    .padding(
                        if (isMessageMine()) PaddingValues(
                            start = chatMaxPadding
                        ) else PaddingValues(
                            end = chatMaxPadding
                        )
                    )
                    .background(
                        color = bubbleColor,
                        shape = RoundedCornerShape(ROUNDED_CORNER_PERCENTAGE_CHAT)
                    )
                    .padding(8.dp)
            ) {
                Column {
                    RepliedBox(textColor)
                    ChatFlexBoxLayout(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(4.dp),
                        text = body,
                        color = textColor,
                        messageStat = {
                            Text(text = createdAt, color = textColor)
                        })
                }
            }
        }
    }

}

@Composable
private fun MessagePresenter.RepliedBox(textColor: Color) {
    repliedMessageBody?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(ROUNDED_CORNER_PERCENTAGE_CHAT)
                )
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = repliedMessageUserName ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    color = textColor
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }

        }
    }
}


@PreviewUtil
@Composable
fun MessageListPreview() {
    MessageList(messageList)
}

@PreviewUtil
@Composable
fun TextMessageItemPreview() {
    TextMessageItem(messageList[0])
}