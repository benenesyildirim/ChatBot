package com.benenesyildirim.chatbot.data.network

import android.util.Log
import com.benenesyildirim.chatbot.common.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.Response
import okhttp3.WebSocketListener
import javax.inject.Inject

class ChatBotWebSocketClient @Inject constructor(
    private val client: OkHttpClient
) {
    private var webSocket: WebSocket? = null

    fun connect(chatListener: ChatBotWebSocketListener) {
        val request = Request.Builder()
            .url(BASE_URL)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                chatListener.onOpen(webSocket, response)
                Log.d("WebSocket", "Connected: $response")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                chatListener.onMessage(webSocket, text)
                Log.d("WebSocket", "Message received: $text")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                chatListener.onFailure(webSocket, t, response)
                Log.e("WebSocket", "Error: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                chatListener.onClosed(webSocket, code, reason)
                Log.d("WebSocket", "Closed: $code")
            }
        })
    }

    fun sendAction(action: String) {
        webSocket?.send(action)
    }

    fun disconnect() {
        webSocket?.close(1000, "Closing connection")
    }
}