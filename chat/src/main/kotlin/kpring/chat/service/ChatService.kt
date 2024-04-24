package kpring.chat.service

import kpring.chat.model.Chat
import kpring.chat.repository.ChatRepository
import kpring.core.chat.dto.request.CreateChatRequest
import kpring.core.global.dto.response.ApiResponse
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository
) {
    /*
     business logic
     */
    fun createChat(
        request: CreateChatRequest, userId: String
    ): ApiResponse<Void> {

        val chat = chatRepository.save(
            Chat(
                userId = userId, roomId = request.room, nickname = request.nickname, content = request.content
            )
        )
        return ApiResponse<Void>(
            200, "success"
        )
    }
}