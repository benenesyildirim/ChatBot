package com.benenesyildirim.chatbot.common

import com.benenesyildirim.chatbot.data.model.ChatContent
import com.benenesyildirim.chatbot.data.model.Step
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class StepDeserializer : JsonDeserializer<Step> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Step {
        val jsonObject = json.asJsonObject

        val step = jsonObject["step"].asString
        val type = jsonObject["type"].asString
        val action = jsonObject["action"].asString

        val content = jsonObject["content"]
        val contentObject = context!!.deserialize<ChatContent>(content, ChatContent::class.java)

        return Step(step, type, contentObject, action)
    }
}

