package com.benenesyildirim.chatbot.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benenesyildirim.chatbot.common.ActionType
import com.benenesyildirim.chatbot.common.Constants.FIRST_STEP
import com.benenesyildirim.chatbot.common.Utils.isValidJson
import com.benenesyildirim.chatbot.common.Utils.loadAndSaveJsonToDatabase
import com.benenesyildirim.chatbot.data.dao.StepDao
import com.benenesyildirim.chatbot.data.local.ChatBotDB
import com.benenesyildirim.chatbot.data.model.ActionButton
import com.benenesyildirim.chatbot.data.model.Content
import com.benenesyildirim.chatbot.data.network.ChatBotWebSocketListener
import com.benenesyildirim.chatbot.domain.ChatBotUseCase
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val chatBotUseCase: ChatBotUseCase,
    private val database: ChatBotDB
) : ViewModel(), ChatBotWebSocketListener {

    private val _step = MutableLiveData<StepDao>()
    val step: LiveData<StepDao> get() = _step

    private val _content = MutableLiveData<Content>()
    val content: LiveData<Content> get() = _content

    private val _action = MutableLiveData<ActionButton>()
    val action: LiveData<ActionButton> get() = _action

    private val _endConversation = MutableLiveData<Boolean>()
    val endConversation: LiveData<Boolean> get() = _endConversation

    init {
        viewModelScope.launch {
            if (chatBotUseCase.getStepsCount() == 0)
                loadAndSaveJsonToDatabase(context, database)

            getStep(FIRST_STEP)
        }
        startChatBot()
    }

    private fun startChatBot() {
        viewModelScope.launch {
            chatBotUseCase.startChatBot(this@ChatBotViewModel)
        }
    }

    private fun sendAction(content: Content) {
        viewModelScope.launch {
            chatBotUseCase.sendAction(Gson().toJson(content))
        }
    }

    private fun endChatBot() {
        viewModelScope.launch {
            chatBotUseCase.endChatBot()
        }
    }

    fun getStep(step: String) {
        when (step) {
            ActionType.END_CONVERSATION.type -> {
                endChatBot()
                _endConversation.postValue(true)
            }

            ActionType.SHOW_GUIDE.type -> {
                Toast.makeText(context, "SHOW_GUIDE", Toast.LENGTH_SHORT).show()
            }

            ActionType.WAIT_CHOICE.type -> {
                Toast.makeText(context, "WAIT_CHOICE", Toast.LENGTH_SHORT).show()
            }

            else -> {
                viewModelScope.launch {
                    _step.postValue(chatBotUseCase.getStep(step))
                }
            }
        }
    }

    fun getContent(contentId: Int) {
        viewModelScope.launch {
            val contentDao = chatBotUseCase.getContent(contentId)
            if (contentDao != null)
                sendAction(contentDao.toContent())
        }
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("ChatBot App", "onOpen: ChatBotViewModel")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        try {
            if (isValidJson(text)) {
                val content = Gson().fromJson(text, Content::class.java)
                _content.postValue(content)
            } else {
                Log.e("ChatBot App", "Invalid JSON: $text")
            }
        } catch (e: JsonSyntaxException) {
            Log.e("ChatBot App", "Failed to parse message: $text", e)
        }
        Log.d("ChatBot App", "onMessage: message = $text")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("ChatBot App", "onFailure: ChatBotViewModel")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("ChatBot App", "onClosed: ChatBotViewModel")
    }
}