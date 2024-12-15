package com.benenesyildirim.chatbot.common

import com.benenesyildirim.chatbot.data.model.ActionButton
import com.benenesyildirim.chatbot.data.model.ChatContent
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ContentDeserializer : JsonDeserializer<ChatContent> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ChatContent {
        if (!json.isJsonObject) {
            return if (json.asString.contains("https"))
                ChatContent.ImageContent(json.asString)
            else
                ChatContent.TextContent(json.asString)
        }

        val jsonObject = json.asJsonObject
        return when {
            jsonObject.has("buttons") -> {
                val text = jsonObject["text"].asString
                val buttons = context!!.deserialize<List<ActionButton>>(
                    jsonObject["buttons"],
                    object : TypeToken<List<ActionButton>>() {}.type
                )
                ChatContent.ButtonContent(text, buttons)
            }

            jsonObject.has("text") -> {
                val text = jsonObject["text"].asString
                ChatContent.TextContent(text)
            }

            jsonObject.has("image") -> {
                val imageUrl = jsonObject["imageUrl"].asString
                ChatContent.ImageContent(imageUrl)
            }

            else -> throw JsonSyntaxException("Unknown content type")
        }
    }
}