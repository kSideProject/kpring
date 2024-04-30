package kpring.chat.chatroom.service

import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository
) {
    fun createChatRoom(
        request: CreateChatRoomRequest,
        userId: String
    ){
        val chatRoom = chatRoomRepository.save(ChatRoom(request.users))
    }
}