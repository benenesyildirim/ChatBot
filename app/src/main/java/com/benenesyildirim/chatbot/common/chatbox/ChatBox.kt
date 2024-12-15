package com.benenesyildirim.chatbot.common.chatbox

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.benenesyildirim.chatbot.R
import com.benenesyildirim.chatbot.data.model.ActionButton
import com.benenesyildirim.chatbot.databinding.CustomViewChatBoxBinding
import com.bumptech.glide.Glide

class ChatBox(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        0,
    )

    private val binding: CustomViewChatBoxBinding =
        CustomViewChatBoxBinding.inflate(LayoutInflater.from(context), this, true)

    fun setActionList(actions: List<ActionButton>, listener: ChatBoxActionSelection) {
        if (actions.isEmpty()) return
        binding.chatOptionsRv.layoutManager = LinearLayoutManager(context)
        binding.chatOptionsRv.adapter = ChatBoxActionsAdapter(actions) { action ->
            listener.actionSelected(action)
        }
    }

    fun setTitle(title: String) {
        binding.chatTv.text = title
    }

    fun showImage(imageURL: String) {
        binding.chatTv.visibility = GONE
        binding.chatOptionsRv.visibility = GONE

        Glide.with(this)
            .load(imageURL)
            .placeholder(R.drawable.app_placeholder_image)
            .into(binding.chatIv)

        binding.chatIv.visibility = VISIBLE
    }

    fun setChatBoxType(isMessageFromUser: Boolean) {
        if (isMessageFromUser) {
            binding.companyProfileIv.visibility = View.INVISIBLE
            binding.userProfileIv.visibility = View.VISIBLE
            binding.chatBoxCv.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_light))
        } else {
            binding.companyProfileIv.visibility = View.VISIBLE
            binding.userProfileIv.visibility = View.INVISIBLE
            binding.chatBoxCv.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple))
        }
    }

}

interface ChatBoxActionSelection{
    fun actionSelected(action: ActionButton)
}