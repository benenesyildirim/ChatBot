package com.benenesyildirim.chatbot.common.chatbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benenesyildirim.chatbot.data.model.ActionButton
import com.benenesyildirim.chatbot.databinding.RowDesignActionButtonBinding

class ChatBoxActionsAdapter(
    private val actions: List<ActionButton>,
    val listener: (ActionButton) -> Unit
) : RecyclerView.Adapter<ChatBoxActionsAdapter.ChatBoxViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatBoxViewHolder {
        val binding =
            RowDesignActionButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ChatBoxViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatBoxViewHolder, position: Int) {
        val action = actions[position]
        holder.bind(action)

        holder.binding.actionButtonTv.setOnClickListener {
            listener(actions[position])
        }
    }

    override fun getItemCount() = actions.size

    class ChatBoxViewHolder(val binding: RowDesignActionButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(action: ActionButton) {
            binding.actionButtonTv.text = action.label
        }
    }
}