package com.example.wonderfulcompose.ui.profile

import android.text.SpannableStringBuilder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.example.wonderfulcompose.R
import com.example.wonderfulcompose.components.PreviewUtil
import com.example.wonderfulcompose.components.ROUNDED_CORNER_PERCENTAGE_CHAT
import com.example.wonderfulcompose.components.annotatedStringResource
import com.example.wonderfulcompose.components.chatflexbox.ChatFlexBoxLayout
import com.example.wonderfulcompose.components.chatflexbox.ChatRowData
import com.example.wonderfulcompose.data.fake.messageList
import com.example.wonderfulcompose.data.models.MessagePresenter
import com.example.wonderfulcompose.data.models.isMessageMine
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import org.commonmark.Extension
import org.commonmark.ext.autolink.AutolinkExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer


@Composable
fun MessageList(messageList: List<MessagePresenter>) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messageList) { message ->
                TextMessageItem(message)
            }
        }
        val text = remember { mutableStateOf("Type Something...") }

        MessengerInput(text = text)
    }
}

@Composable
private fun MessengerInput(text: MutableState<String>) {
    Box(
        modifier = Modifier
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
//                    .width(IntrinsicSize.Max)
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
                    val chatRowData = remember { ChatRowData() }
                    UserName(messagePresenter, textColor)
                    RepliedBox(textColor)
                    ChatFlexBoxLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        message = {
                            val text = longText()
                            RichTextSolution(text)
//                            JavaCommonMark(text, chatRowData)
                        },
                        messageStat = {
                            Text(text = createdAt, color = textColor)
                        },
                        chatRowData = chatRowData
                    )
                }
            }
        }
    }
}

@Composable
private fun RichTextSolution(text: String) {
    RichText {
        Markdown(content = text)
    }
}

@Composable
private fun longText() = """
    # Demo
    
    <i>World</i>

    Emphasis, aka italics, with *asterisks* or _underscores_. Strong emphasis, aka bold, with **asterisks** or __underscores__. Combined emphasis with **asterisks and _underscores_**. [Links with two blocks, text in square-brackets, destination is in parentheses.](https://www.example.com). Inline `code` has `back-ticks around` it.
    1. First ordered list item
    2. Another item
        * Unordered sub-list.
    3. And another item.
        You can have properly indented paragraphs within list items. Notice the blank line above, and the leading spaces (at least one, but we'll use three here to also align the raw Markdown).

    * Unordered list can use asterisks
    - Or minuses
    + Or pluses
    ---

    ```javascript
    var s = "code blocks use monospace font";
    alert(s);
    ```

    Markdown | Table | Extension
    --- | --- | ---
    *renders* | `beautiful images` | ![random image](https://picsum.photos/seed/picsum/400/400 "Text 1")
    1 | 2 | 3

    > Blockquotes are very handy in email to emulate reply text.
    > This line is part of the same quote.
    """.trimIndent()

@Composable
private fun JavaCommonMark(
    text: String,
    chatRowData: ChatRowData
) {
    val extensions: List<Extension> =
        listOf(
            AutolinkExtension.create(),
            HeadingAnchorExtension.create(),
            TablesExtension.create()
        )
    val parser: Parser = Parser.builder().extensions(extensions).build()
    val document = parser.parse(text)
    val renderer = HtmlRenderer.builder().extensions(extensions).build()
    val html = renderer.render(document)

    val spannableString = SpannableStringBuilder(html).toString()
    val spanned = HtmlCompat.fromHtml(
        spannableString,
        HtmlCompat.FROM_HTML_MODE_COMPACT
    )

    Text(text = annotatedStringResource(text),
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            chatRowData.text = text
            // maxWidth of text constraint returns parent maxWidth - horizontal padding
            chatRowData.lineCount = textLayoutResult.lineCount
            chatRowData.lastLineWidth =
                textLayoutResult.getLineRight(chatRowData.lineCount - 1)
            chatRowData.textWidth = textLayoutResult.size.width
        })
}

//fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
//    val spanned = this@toAnnotatedString
//    append(spanned.toString())
//    getSpans(0, spanned.length, Any::class.java).forEach { span ->
//        val start = getSpanStart(span)
//        val end = getSpanEnd(span)
//        when (span) {
//            is StyleSpan -> when (span.style) {
//                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
//                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
//                Typeface.BOLD_ITALIC -> addStyle(
//                    SpanStyle(
//                        fontWeight = FontWeight.Bold,
//                        fontStyle = FontStyle.Italic
//                    ), start, end
//                )
//            }
//
//            is UnderlineSpan -> addStyle(
//                SpanStyle(textDecoration = TextDecoration.Underline),
//                start,
//                end
//            )
//
//            is ForegroundColorSpan -> addStyle(
//                SpanStyle(color = Color(span.foregroundColor)),
//                start,
//                end
//            )
//        }
//    }
//}

@Composable
private fun UserName(
    messagePresenter: MessagePresenter,
    textColor: Color
) {
    Text(
        text = messagePresenter.userName,
        color = textColor,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(
            PaddingValues(
                start = 8.dp,
                end = 8.dp
            )
        )
    )
}

@Composable
private fun MessagePresenter.RepliedBox(textColor: Color) {
    repliedMessageBody?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(top = 2.dp))
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