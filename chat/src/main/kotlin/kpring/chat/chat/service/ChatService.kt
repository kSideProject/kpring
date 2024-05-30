package kpring.chat.chat.service

import kpring.chat.chat.model.Chat
import kpring.chat.chat.model.ServerChat
import kpring.chat.chat.repository.RoomChatRepository
import kpring.chat.chat.repository.ServerChatRepository
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.ChatType
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ChatService(
  private val roomChatRepository: RoomChatRepository,
  private val serverChatRepository: ServerChatRepository,
  private val chatRoomRepository: ChatRoomRepository,
  @Value("\${page.size}") val pageSize: Int = 100,
) {
  fun createChat(
    request: CreateChatRequest,
    userId: String,
  ): Boolean {
    if (request.type == ChatType.Room) {
      return createRoomChat(request, userId)
    } else {
      return createServerChat(request, userId)
    }
  }

  fun createRoomChat(
    request: CreateChatRequest,
    userId: String,
  ): Boolean {
    val chat =
      roomChatRepository.save(
        Chat(
          userId = userId,
          roomId = request.id,
          content = request.content,
        ),
      )
    return true
  }

  fun createServerChat(
    request: CreateChatRequest,
    userId: String,
  ): Boolean {
    val chat =
      serverChatRepository.save(
        ServerChat(
          userId = userId,
          serverId = request.id,
          content = request.content,
        ),
      )
    return true
  }

  fun getChatsByChatRoom(
    chatRoomId: String,
    userId: String,
    page: Int,
  ): List<ChatResponse> {
    checkIfAuthorized(chatRoomId, userId)

    // find chats by chatRoomId and convert them into DTOs
    val pageable: Pageable = PageRequest.of(page, pageSize)
    val chats: List<Chat> = roomChatRepository.findAllByRoomId(chatRoomId, pageable)

    return convertChatsToResponses(chats)
  }

  fun checkIfAuthorized(
    chatRoomId: String,
    userId: String,
  ) {
    // check if there is a chatroom with the chatRoomId and the user is one of the members
    if (!chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId)) {
      throw GlobalException(ErrorCode.UNAUTHORIZED_CHATROOM)
    }
  }

  fun convertChatsToResponses(chats: List<Chat>): List<ChatResponse> {
    val chatResponses =
      chats.map { chat ->
        ChatResponse(chat.roomId, chat.isEdited(), chat.createdAt, chat.content)
      }
    return chatResponses
  }
}
