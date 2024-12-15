package com.benenesyildirim.chatbot.data.model

import com.benenesyildirim.chatbot.data.dao.ContentDao
import com.google.gson.Gson

data class Content(
    val text: String?,
    val imageUrl: String?,
    val type: String?,
    val buttons: List<ActionButton>?
) {
    fun toContentDao() = ContentDao(
        text = text,
        imageUrl = imageUrl,
        type = type,
        buttons = Gson().toJson(buttons)
    )
}
