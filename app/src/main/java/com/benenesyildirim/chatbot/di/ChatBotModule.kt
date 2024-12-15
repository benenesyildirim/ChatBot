package com.benenesyildirim.chatbot.di

import android.content.Context
import androidx.room.Room
import com.benenesyildirim.chatbot.common.Constants.DB_NAME
import com.benenesyildirim.chatbot.data.local.ChatBotDB
import com.benenesyildirim.chatbot.data.network.ChatBotWebSocketClient
import com.benenesyildirim.chatbot.data.repository.ChatBotRepository
import com.benenesyildirim.chatbot.domain.ChatBotUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatBotModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideWebSocketClient(client: OkHttpClient): ChatBotWebSocketClient {
        return ChatBotWebSocketClient(client)
    }

    @Provides
    @Singleton
    fun provideWebSocketRepository(chatBotWebSocketClient: ChatBotWebSocketClient): ChatBotRepository {
        return ChatBotRepository(chatBotWebSocketClient)
    }

    @Provides
    @Singleton
    fun provideChatBotDatabase(@ApplicationContext app: Context): ChatBotDB {
        return Room.databaseBuilder(app, ChatBotDB::class.java, DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideWebSocketUseCase(chatBotRepository: ChatBotRepository, chatBotDB: ChatBotDB ): ChatBotUseCase {
        return ChatBotUseCase(chatBotRepository, chatBotDB)
    }
}