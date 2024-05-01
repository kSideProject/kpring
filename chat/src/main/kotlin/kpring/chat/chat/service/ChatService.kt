package kpring.chat.chat.service

import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val chatRoomRepository: ChatRoomRepository
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
        chatRoomId: String, userId: String
    ) : List<ChatResponse>{

        checkIfUserBelongToChatRoom(chatRoomId, userId)
        val chats : List<Chat> = chatRepository.findAllByRoomId(chatRoomId)
        val chatResponses = chats.map { chat ->
            ChatResponse(chat.roomId, chat.isDeleted, chat.isEdited, chat.sentAt, chat.content)
        }

        return chatResponses
    }

    fun checkIfUserBelongToChatRoom(chatRoomId: String, userId: String) {
        val chatroom : ChatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow{GlobalException(ErrorCode.CHATROOM_NOT_FOUND)}
        for(member in chatroom.getUsers()){
            if(member.equals(userId)){
                return;
            }
        }
        throw GlobalException(ErrorCode.UNAUTHORIZED_CHATROOM)
    }
}