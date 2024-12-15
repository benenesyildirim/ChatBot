package com.benenesyildirim.chatbot.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.benenesyildirim.chatbot.data.dao.ContentDao
import com.benenesyildirim.chatbot.data.dao.StepDao

@Dao
interface ChatBotDao {
    @Insert
    suspend fun insertContent(content: ContentDao): Long

    @Insert
    suspend fun insertStep(step: StepDao)

    @Query("SELECT COUNT(*) FROM step_table")
    suspend fun getStepsCount(): Int

    @Query("SELECT * FROM content_table WHERE id = :contentId")
    suspend fun getContent(contentId: Int): ContentDao?

    @Query("SELECT * FROM step_table WHERE step = :step")
    suspend fun getStep(step: String): StepDao
}