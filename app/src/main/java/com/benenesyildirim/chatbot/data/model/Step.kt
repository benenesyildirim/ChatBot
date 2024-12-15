package com.benenesyildirim.chatbot.data.model

data class Step(
    val step: String,
    val type: String,
    val content: ChatContent,
    val action: String
)