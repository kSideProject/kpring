package kpring.chat.chat.service

import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import kpring.core.chat.chat.dto.request.CreateChatRequest
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
    ): String {

        val chat = chatRepository.save(
            Chat(
                userId = userId, roomId = request.room, nickname = request.nickname, content = request.content
            )
        )

        return "success"
    }
}