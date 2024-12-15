package com.benenesyildirim.chatbot.common

import android.content.Context
import android.widget.Toast
import com.benenesyildirim.chatbot.R
import com.benenesyildirim.chatbot.data.dao.StepDao
import com.benenesyildirim.chatbot.data.local.ChatBotDB
import com.benenesyildirim.chatbot.data.model.ChatContent
import com.benenesyildirim.chatbot.data.model.Content
import com.benenesyildirim.chatbot.data.model.Step
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

object Utils {
    suspend fun loadAndSaveJsonToDatabase(context: Context, database: ChatBotDB) {
        Toast.makeText(context, "loadAndSaveJsonToDatabase", Toast.LENGTH_SHORT).show()

        val inputStream = context.resources.openRawResource(R.raw.live_support_flow)
        val reader = InputStreamReader(inputStream)
        val gson = GsonBuilder()
            .registerTypeAdapter(Step::class.java, StepDeserializer())
            .registerTypeAdapter(ChatContent::class.java, ContentDeserializer())
            .create()

        val stepsData: List<Step> = gson.fromJson(reader, object : TypeToken<List<Step>>() {}.type)
        withContext(Dispatchers.IO) {
            for (stepData in stepsData) {
                val content = getContent(stepData)
                val contentId = database.chatBotDao().insertContent(content.toContentDao())

                val step = StepDao(
                    step = stepData.step,
                    type = stepData.type,
                    contentId = contentId.toInt(),
                    action = stepData.action
                )

                database.chatBotDao().insertStep(step)
            }
        }
    }

    private fun getContent(step: Step): Content {
        return when (val content = step.content) {
            is ChatContent.TextContent -> {
                Content(
                    text = content.text,
                    imageUrl = null,
                    type = step.type,
                    buttons = listOf()
                )
            }
            is ChatContent.ImageContent -> {
                Content(
                    text = null,
                    imageUrl = content.imageUrl,
                    type = step.type,
                    buttons = listOf()
                )
            }
            is ChatContent.ButtonContent -> {
                Content(
                    text = content.text,
                    imageUrl = null,
                    type = step.type,
                    buttons = content.buttons
                )
            }
            else -> { throw IllegalArgumentException("Unknown content type") }
        }
    }

    fun isValidJson(text: String): Boolean {
        return try {
            Gson().fromJson(text, JsonObject::class.java)
            true
        } catch (e: JsonSyntaxException) {
            false
        }
    }
}