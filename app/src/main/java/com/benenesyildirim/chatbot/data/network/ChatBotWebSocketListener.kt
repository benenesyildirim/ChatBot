package com.benenesyildirim.chatbot.data.network

import okhttp3.Response
import okhttp3.WebSocket

interface ChatBotWebSocketListener {
    fun onOpen(webSocket: WebSocket, response: Response)
    fun onMessage(webSocket: WebSocket, text: String)
    fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?)
    fun onClosed(webSocket: WebSocket, code: Int, reason: String)
}