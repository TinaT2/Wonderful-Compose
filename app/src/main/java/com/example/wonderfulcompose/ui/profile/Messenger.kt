package com.example.wonderfulcompose.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.data.fake.catAdoptionDiscussion
import com.example.wonderfulcompose.data.models.MessagePresenter

@Composable
fun MessageList(messageList: List<MessagePresenter>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(messageList) { message ->
                TextMessageItem(
                    body = message.body,
                    userName = message.userName,
                    message.createdAt
                )
            }
        }
        var text = remember { mutableStateOf("Type Something...") }

//        Spacer(modifier = Modifier.weight(weight =2F,fill = true))

        MessengerInput(text = text, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun MessengerInput(text: MutableState<String>, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.BottomCenter
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

//@Composable
//fun TextMessageItem(body: String, userName: String, time: String) {
//    Box(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(8.dp)
//    ) {
//        Text(
//            text = body,
//            textAlign = TextAlign.Start,
//            modifier = Modifier.align(Alignment.TopStart),
//            overflow = TextOverflow.Visible
//        )
//        Text(
//            text = time,
//            textAlign = TextAlign.End,
//            modifier = Modifier.align(Alignment.BottomEnd)
//
//        )
//    }
//}
@Composable
fun TextMessageItem(body: String, userName: String, time: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Text(
            text = body,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.TopStart),
            overflow = TextOverflow.Visible
        )
        Text(
            text = time,
            textAlign = TextAlign.End,
            modifier = Modifier.align(Alignment.BottomEnd)

        )
    }
}


@PreviewUtil
@Composable
fun MessageListPreview() {
    MessageList(catAdoptionDiscussion)
}

@PreviewUtil
@Composable
fun TextMessageItemPreview() {
    TextMessageItem(
        body = "Absolutely! Looking forward to the joy they’ll bring. Let’s",
        userName = "John",
        "06:06"
    )
}