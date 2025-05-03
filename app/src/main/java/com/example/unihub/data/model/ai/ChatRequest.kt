package com.example.unihub.data.model.ai

// ChatRequest.kt
data class ChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message>
)

data class Message(
    val role: String, // "user" or "assistant"
    val content: String
)

// ChatResponse.kt
data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

