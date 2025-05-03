package com.example.unihub.data.api

import com.example.unihub.data.model.ai.ChatRequest
import com.example.unihub.data.model.ai.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatGptApi {
    @POST("v1/chat/completions")
    suspend fun sendMessage(
        @Body request: ChatRequest,
        @Header("Authorization") authHeader: String
    ): ChatResponse
}
