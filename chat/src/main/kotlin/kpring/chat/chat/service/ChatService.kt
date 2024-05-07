package kpring.chat.chat.service

import kpring.chat.chat.dto.ChatInfo
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import kpring.core.chat.chat.dto.request.CreateChatRequest
import org.springframework.data.domain.Pageable
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
    ) {

        val chat = chatRepository.save(
            Chat(
                userId = userId, roomId = request.room, nickname = request.nickname, content = request.content
            )
        )
    }

    fun getChatList(userId: String, pageable: Pageable): List<ChatInfo>{
        return chatRepository.findByUserId(userId, pageable).map { entity ->
            ChatInfo(
                chatId = entity.id!!,
                userId = entity.userId,
                roomId = entity.roomId,
                nickname = entity.nickname,
                content = entity.content
            )
        }
    }
}