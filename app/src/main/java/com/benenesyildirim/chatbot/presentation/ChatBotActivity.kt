package com.benenesyildirim.chatbot.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benenesyildirim.chatbot.R
import com.benenesyildirim.chatbot.common.chatbox.ChatBoxActionSelection
import com.benenesyildirim.chatbot.data.model.ActionButton
import com.benenesyildirim.chatbot.data.model.Content
import com.benenesyildirim.chatbot.databinding.ActivityChatBotBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatBotActivity : AppCompatActivity(), ChatBoxActionSelection {
    private lateinit var binding: ActivityChatBotBinding
    private val viewModel: ChatBotViewModel by viewModels()
    private lateinit var chatBotAdapter: ChatBotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        chatBotAdapter = ChatBotAdapter(this)
        setChatBotRV()
        initObservers()
    }

    private fun initObservers() {
        viewModel.step.observe(this) { step ->
            if (step != null)
                viewModel.getContent(step.contentId)
        }

        viewModel.content.observe(this) { content ->
            if (content != null) {
                chatBotAdapter.addContent(content, false)
            }
        }

        viewModel.action.observe(this) { action ->
            Toast.makeText(this, action.toString(), Toast.LENGTH_SHORT).show()
        }

        viewModel.endConversation.observe(this){
            if (it) {
                Toast.makeText(this, R.string.end_conversation, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setChatBotRV() {
        binding.chatListRv.layoutManager = LinearLayoutManager(this)
        binding.chatListRv.adapter = chatBotAdapter
    }

    override fun actionSelected(action: ActionButton) {
        val userContent = Content(
            text = action.label,
            imageUrl = null,
            type = null,
            buttons = null
        )
        chatBotAdapter.addContent(userContent, true)
        viewModel.getStep(action.action)
    }
}