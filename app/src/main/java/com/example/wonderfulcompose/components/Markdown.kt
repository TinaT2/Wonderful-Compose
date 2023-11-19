package com.example.wonderfulcompose.components

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.viewinterop.AndroidView
import com.halilibo.richtext.markdown.HtmlBlock
import com.halilibo.richtext.markdown.MarkdownRichText
import com.halilibo.richtext.markdown.RenderTable
import com.halilibo.richtext.markdown.childrenSequence
import com.halilibo.richtext.markdown.filterChildrenType
import com.halilibo.richtext.markdown.node.AstInlineNodeType
import com.halilibo.richtext.markdown.node.AstListItem
import com.halilibo.richtext.markdown.node.AstNode
import com.halilibo.richtext.markdown.visitChildren
import com.halilibo.richtext.ui.BlockQuote
import com.halilibo.richtext.ui.CodeBlock
import com.halilibo.richtext.ui.FormattedList
import com.halilibo.richtext.ui.Heading
import com.halilibo.richtext.ui.HorizontalRule
import com.halilibo.richtext.ui.ListType.Ordered
import com.halilibo.richtext.ui.ListType.Unordered
import com.halilibo.richtext.ui.RichTextScope
import com.halilibo.richtext.ui.string.InlineContent
import com.halilibo.richtext.ui.string.Text
import com.halilibo.richtext.ui.string.richTextString
import org.commonmark.ext.gfm.tables.TableBlock
import org.commonmark.ext.gfm.tables.TableBody
import org.commonmark.ext.gfm.tables.TableCell
import org.commonmark.ext.gfm.tables.TableHead
import org.commonmark.ext.gfm.tables.TableRow
import org.commonmark.node.BlockQuote
import org.commonmark.node.BulletList
import org.commonmark.node.Code
import org.commonmark.node.Document
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.Heading
import org.commonmark.node.HtmlBlock
import org.commonmark.node.Link
import org.commonmark.node.LinkReferenceDefinition
import org.commonmark.node.ListItem
import org.commonmark.node.OrderedList
import org.commonmark.node.Paragraph
import org.commonmark.node.Text
import org.commonmark.node.ThematicBreak

/**
 * A composable that renders Markdown content using RichText.
 *
 * @param content Markdown text. No restriction on length.
 * @param markdownParseOptions Options for the Markdown parser.
 * @param onLinkClicked A function to invoke when a link is clicked from rendered content.
 */
@Composable
public fun String.Markdown(
    content: String,
    onLinkClicked: ((String) -> Unit)? = null
) {
    val onLinkClickedState = rememberUpdatedState(onLinkClicked)
    // Can't use UriHandlerAmbient.current::openUri here,
    // see https://issuetracker.google.com/issues/172366483
    val realLinkClickedHandler = onLinkClickedState.value ?: LocalUriHandler.current.let {
        remember {
            { url -> it.openUri(url) }
        }
    }
    CompositionLocalProvider(LocalOnLinkClicked provides realLinkClickedHandler) {
        val markdownAst = parsedMarkdownAst(text = content)
        RecursiveRenderMarkdownAst(astNode = markdownAst)
    }
}

@Suppress("IMPLICIT_CAST_TO_ANY")
@Composable
internal fun RichTextScope.RecursiveRenderMarkdownAst(astNode: AstNode?) {
    astNode ?: return

    when (val astNodeType = astNode.type) {
        is Document -> visitChildren(node = astNode)
        is BlockQuote -> {
            BlockQuote {
                visitChildren(astNode)
            }
        }

        is BulletList -> {
            FormattedList(
                listType = Unordered,
                items = astNode.filterChildrenType<AstListItem>().toList()
            ) {
                visitChildren(it)
            }
        }

        is OrderedList -> {
            FormattedList(
                listType = Ordered,
                items = astNode.childrenSequence().toList()
            ) { astListItem ->
                visitChildren(astListItem)
            }
        }

        is ThematicBreak -> {
            HorizontalRule()
        }

        is Heading -> {
            Heading(level = astNodeType.level) {
                MarkdownRichText(astNode, Modifier.semantics { heading() })
            }
        }

        is Code -> {
            CodeBlock(text = astNodeType.literal.trim())
        }

        is FencedCodeBlock -> {
            CodeBlock(text = astNodeType.literal.trim())
        }

        is HtmlBlock -> {
            Text(text = richTextString {
                appendInlineContent(content = InlineContent {
                    AndroidView(
                        factory = { context ->
                            // TODO: pass current styling to legacy TextView
                            TextView(context)
                        },
                        update = {
                            it.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Html.fromHtml(astNodeType.literal, 0)
                            } else {
                                @Suppress("DEPRECATION")
                                Html.fromHtml(astNodeType.literal)
                            }
                        }
                    )
                })
            })
        }

        is Link, LinkReferenceDefinition -> {
            // TODO(halilozercan)
            /* no-op */
        }

        is Paragraph -> {
            MarkdownRichText(astNode)
        }

        is TableBlock -> {
            RenderTable(astNode)
        }
        // This should almost never happen. All the possible text
        // nodes must be under either Heading, Paragraph or CustomNode
        // In any case, we should include it here to prevent any
        // non-rendered text problems.
        is Text -> {
            // TODO(halilozercan) use multiplatform compatible stderr logging
            println("Unexpected raw text while traversing the Abstract Syntax Tree.")
            Text(richTextString { append(astNodeType.literal) })
        }

        is ListItem -> {
            println("MarkdownRichText: Unexpected AstListItem while traversing the Abstract Syntax Tree.")
        }

        is AstInlineNodeType -> {
            // ignore
            println("MarkdownRichText: Unexpected AstInlineNodeType $astNodeType while traversing the Abstract Syntax Tree.")
        }

        org.commonmark.ext.gfm.tables.TableBody,
        org.commonmark.ext.gfm.tables.TableHead,
        org.commonmark.ext.gfm.tables.TableRow,
        is TableCell -> {
            println("MarkdownRichText: Unexpected Table node while traversing the Abstract Syntax Tree.")
        }
    }.let {}
}

/**
 * Visit and render children from first to last.
 *
 * @param node Root ASTNode whose children will be visited.
 */
@Composable
internal fun RichTextScope.visitChildren(node: AstNode?) {
    node?.childrenSequence()?.forEach {
        RecursiveRenderMarkdownAst(astNode = it)
    }
}

/**
 * An internal ambient to pass through OnLinkClicked function from root [Markdown] composable
 * to children that render links. Although being explicit is preferred, recursive calls to
 * [visitChildren] increases verbosity with each extra argument.
 */
internal val LocalOnLinkClicked =
    compositionLocalOf<(String) -> Unit> { error("OnLinkClicked is not provided") }
