package com.benenesyildirim.chatbot.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.benenesyildirim.chatbot.data.model.ActionButton
import com.benenesyildirim.chatbot.data.model.Content
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "content_table")
data class ContentDao(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String?,
    val imageUrl: String?,
    val type: String?,
    val buttons: String?
){
    fun toContent() = Content(
        text = text,
        imageUrl = imageUrl,
        type = type,
        buttons = Gson().fromJson(buttons, object : TypeToken<List<ActionButton>>() {}.type)
    )
}