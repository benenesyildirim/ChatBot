package com.benenesyildirim.chatbot.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benenesyildirim.chatbot.common.chatbox.ChatBoxActionSelection
import com.benenesyildirim.chatbot.data.model.Content
import com.benenesyildirim.chatbot.databinding.RowDesignChatBoxBinding

class ChatBotAdapter(private val listener: ChatBoxActionSelection) :
    RecyclerView.Adapter<ChatBotAdapter.ChatBotViewHolder>() {
    private val conversation: MutableList<Content> = mutableListOf()
    private var isUserAnswer = false

    fun addContent(content: Content, isUserAnswer: Boolean) {
        conversation.add(content)
        this.isUserAnswer = isUserAnswer
        notifyItemInserted(conversation.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatBotViewHolder {
        val binding =
            RowDesignChatBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatBotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatBotViewHolder, position: Int) {
        val currentContent = conversation[position]
        holder.bind(currentContent, isUserAnswer, listener)
    }

    override fun getItemCount() = conversation.size

    class ChatBotViewHolder(private val binding: RowDesignChatBoxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            content: Content,
            isUserAnswer: Boolean,
            listener: ChatBoxActionSelection
        ) {
            binding.chatBox.setChatBoxType(isUserAnswer)

            if (!content.text.isNullOrEmpty()) {
                binding.chatBox.setTitle(content.text)
            }
            if (!content.imageUrl.isNullOrEmpty()) {
                binding.chatBox.showImage(content.imageUrl)
            }
            if (!content.buttons.isNullOrEmpty()) {
                binding.chatBox.setActionList(content.buttons, listener)
            }
        }
    }
}