package com.benenesyildirim.chatbot.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_table")
data class StepDao(
    @PrimaryKey val step: String,
    val type: String,
    val contentId: Int,
    val action: String
)