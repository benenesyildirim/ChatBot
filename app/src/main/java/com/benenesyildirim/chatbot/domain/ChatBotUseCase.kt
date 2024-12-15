package com.benenesyildirim.chatbot.domain

import com.benenesyildirim.chatbot.data.local.ChatBotDB
import com.benenesyildirim.chatbot.data.network.ChatBotWebSocketListener
import com.benenesyildirim.chatbot.data.repository.ChatBotRepository
import javax.inject.Inject

class ChatBotUseCase @Inject constructor(
    private val chatBotRepository: ChatBotRepository,
    private val chatBotDatabase: ChatBotDB
) {
    fun startChatBot(chatListener: ChatBotWebSocketListener) =
        chatBotRepository.connectChatBot(chatListener)

    fun sendAction(action: String) = chatBotRepository.sendAction(action)

    fun endChatBot() = chatBotRepository.disconnectChatBot()

    suspend fun getStepsCount() = chatBotDatabase.chatBotDao().getStepsCount()

    suspend fun getStep(step: String) = chatBotDatabase.chatBotDao().getStep(step)

    suspend fun getContent(contentId: Int) = chatBotDatabase.chatBotDao().getContent(contentId)
}