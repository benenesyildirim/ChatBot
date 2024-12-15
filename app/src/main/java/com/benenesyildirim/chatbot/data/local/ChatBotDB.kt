package com.benenesyildirim.chatbot.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benenesyildirim.chatbot.data.dao.ContentDao
import com.benenesyildirim.chatbot.data.dao.StepDao

@Database(entities = [StepDao::class, ContentDao::class], version = 1)
abstract class ChatBotDB: RoomDatabase() {
    abstract fun chatBotDao(): ChatBotDao
}