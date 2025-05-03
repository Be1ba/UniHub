package com.example.unihub.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ChatGptApi
import com.example.unihub.data.api.ServiceBuilderGpt
import com.example.unihub.data.model.ai.ChatRequest
import com.example.unihub.data.model.ai.Message
import com.example.unihub.utils.Constants
import kotlinx.coroutines.launch

class ChatBotViewModel : ViewModel() {

    private val chatApi = ServiceBuilderGpt.buildService(ChatGptApi::class.java)

    private val _botMessage = MutableLiveData<String>()
    val botMessage: LiveData<String> = _botMessage

    fun sendMessageToBot(userInput: String) {
        viewModelScope.launch {
            try {
                val systemMessage = Message(
                    role = "system",
                    content = """
                    Ты — виртуальный помощник UniHub, мобильного приложения для студентов университета СДУ. 
                    Твоя задача — помогать студентам с вопросами о клубах, мероприятиях, расписании, а также давать советы по студенческой и академической жизни.
                    Отвечай дружелюбно и понятно. Если не знаешь ответа — предложи обратиться в администрацию по email yesmakhanbetovaa@gmail.com или телефону 87477006423.
                    Используй Казахский, Русский или Английский язык в зависимости от запроса пользователя.
                """.trimIndent()
                )

                val userMessage = Message(role = "user", content = userInput)

                val request = ChatRequest(
                    model = "gpt-3.5-turbo",
                    messages = listOf(systemMessage, userMessage)
                )


                val response = chatApi.sendMessage(request, "Bearer ${Constants.ChatGPTApi}")
                val reply = response.choices.firstOrNull()?.message?.content ?: "No response"
                _botMessage.postValue(reply)

            } catch (e: Exception) {
                _botMessage.postValue("Error: ${e.message}")
            }
        }
    }

}

