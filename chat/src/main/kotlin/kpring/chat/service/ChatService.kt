package kpring.chat.service

import kpring.chat.model.Chat
import kpring.chat.repository.ChatRepository
import kpring.core.chat.dto.request.CreateChatRequest
import kpring.core.chat.dto.response.CreateChatResponse
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository
) {
    /*
     business logic
     */
    fun createChat(request: CreateChatRequest): CreateChatResponse {

        val chat = chatRepository.save(
            Chat(
                room = request.room, nickname = request.nickname, content = request.content
            )
        )
        return CreateChatResponse(
            200, "success"
        )
    }
}