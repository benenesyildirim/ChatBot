package com.benenesyildirim.chatbot.common

enum class ActionType(val type: String) {
    END_CONVERSATION("end_conversation"),
    WAIT_CHOICE("await_user_choice"),
    SHOW_GUIDE("show_guide")
}