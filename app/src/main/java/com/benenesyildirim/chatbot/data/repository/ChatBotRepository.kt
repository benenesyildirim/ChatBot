package com.benenesyildirim.chatbot.data.repository

import com.benenesyildirim.chatbot.data.network.ChatBotWebSocketClient
import com.benenesyildirim.chatbot.data.network.ChatBotWebSocketListener
import javax.inject.Inject

class ChatBotRepository @Inject constructor(
    private val chatBotWebSocketClient: ChatBotWebSocketClient
) {
    fun connectChatBot(chatListener: ChatBotWebSocketListener) = chatBotWebSocketClient.connect(chatListener)

    fun sendAction(message: String) = chatBotWebSocketClient.sendAction(message)

    fun disconnectChatBot() = chatBotWebSocketClient.disconnect()
}