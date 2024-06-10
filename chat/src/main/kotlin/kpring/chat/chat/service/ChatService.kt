package kpring.chat.chat.service

import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.RoomChatRepository
import kpring.chat.chat.repository.ServerChatRepository
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.request.UpdateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import kpring.core.server.dto.ServerSimpleInfo
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
  fun createRoomChat(
    request: CreateChatRequest,
    userId: String,
  ): Boolean {
    val chat =
      roomChatRepository.save(
        Chat(
          userId = userId,
          contextId = request.id,
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
        Chat(
          userId = userId,
          contextId = request.id,
          content = request.content,
        ),
      )
    return true
  }

  fun getRoomChats(
    chatRoomId: String,
    userId: String,
    page: Int,
  ): List<ChatResponse> {
    verifyChatRoomAccess(chatRoomId, userId)

    val pageable: Pageable = PageRequest.of(page, pageSize)
    val roomChats: List<Chat> = roomChatRepository.findAllByContextId(chatRoomId, pageable)

    return convertChatsToResponses(roomChats)
  }

  fun getServerChats(
    serverId: String,
    userId: String,
    page: Int,
    servers: List<ServerSimpleInfo>,
  ): List<ChatResponse> {
    verifyServerAccess(servers, serverId)

    val pageable: Pageable = PageRequest.of(page, pageSize)
    val chats: List<Chat> = serverChatRepository.findAllByContextId(serverId, pageable)

    return convertChatsToResponses(chats)
  }

  fun updateRoomChat(
    request: UpdateChatRequest,
    userId: String,
  ): Boolean {
    val chat = roomChatRepository.findById(request.id).orElseThrow { GlobalException(ErrorCode.CHAT_NOT_FOUND) }
    chat.verifyAccess(userId)
    chat.updateContent(request.content)
    roomChatRepository.save(chat)
    return true
  }

  fun updateServerChat(
    request: UpdateChatRequest,
    userId: String,
  ): Boolean {
    val chat = serverChatRepository.findById(request.id).orElseThrow { GlobalException(ErrorCode.CHAT_NOT_FOUND) }
    chat.verifyAccess(userId)
    chat.updateContent(request.content)
    serverChatRepository.save(chat)
    return true
  }

  private fun verifyServerAccess(
    servers: List<ServerSimpleInfo>,
    serverId: String,
  ) {
    servers.forEach { info ->
      if (info.id.equals(serverId)) {
        return
      }
    }
    throw GlobalException(ErrorCode.FORBIDDEN_SERVER)
  }

  private fun verifyChatRoomAccess(
    chatRoomId: String,
    userId: String,
  ) {
    if (!chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId)) {
      throw GlobalException(ErrorCode.FORBIDDEN_CHATROOM)
    }
  }

  private fun convertChatsToResponses(chats: List<Chat>): List<ChatResponse> {
    val chatResponse =
      chats.map { chat ->
        ChatResponse(chat.id!!, chat.isEdited(), chat.createdAt.toString(), chat.content)
      }
    return chatResponse
  }
}
