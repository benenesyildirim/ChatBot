package com.benenesyildirim.chatbot.data.model

sealed class ChatContent {
    data class ButtonContent(val text: String, val buttons: List<ActionButton>) : ChatContent()
    data class TextContent(val text: String) : ChatContent()
    data class ImageContent(val imageUrl: String) : ChatContent()
}