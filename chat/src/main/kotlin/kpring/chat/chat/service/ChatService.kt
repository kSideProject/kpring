package kpring.chat.chat.service

import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val chatRoomRepository: ChatRoomRepository,
    @Value("\${page.size}") val pageSize: Int = 100
) {

    /*
     business logic
     */
    fun createChat(
        request: CreateChatRequest, userId: String
    ) {

        val chat = chatRepository.save(
            Chat(
                userId = userId, roomId = request.room, content = request.content
            )
        )
    }

    fun getChatsByChatRoom(
        chatRoomId: String, userId: String, page: Int
    ): List<ChatResponse> {

        //check if there is a chatroom with the chatRoomId and the user is one of the members
        if (!chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId)) {
            throw GlobalException(ErrorCode.UNAUTHORIZED_CHATROOM)
        }
        val pageable: Pageable = PageRequest.of(page, pageSize)

        //find chats by chatRoomId and convert them into DTOs
        val chats: List<Chat> = chatRepository.findAllByRoomId(chatRoomId, pageable)
        val chatResponses = chats.map { chat ->
            ChatResponse(chat.roomId,chat.isEdited(),chat.createdAt, chat.content)
        }

        return chatResponses
    }
}