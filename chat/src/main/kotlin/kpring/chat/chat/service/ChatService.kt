package kpring.chat.chat.service

import kpring.chat.chat.api.v1.WebSocketChatController
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.RoomChatRepository
import kpring.chat.chat.repository.ServerChatRepository
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.request.UpdateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import kpring.core.chat.model.MessageType
import kpring.core.server.dto.ServerSimpleInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ChatService(
  private val roomChatRepository: RoomChatRepository,
  private val serverChatRepository: ServerChatRepository,
  private val chatRoomRepository: ChatRoomRepository,
  @Value("\${page.size}") val pageSize: Int = 100,
) {
  private val logger: Logger = LoggerFactory.getLogger(WebSocketChatController::class.java)

  fun createRoomChat(
    request: CreateChatRequest,
    userId: String,
  ): ChatResponse {
    val chat =
      roomChatRepository.save(
        Chat(
          userId = userId,
          contextId = request.contextId,
          content = request.content,
        ),
      )
    return ChatResponse(chat.id!!, userId, MessageType.CHAT, chat.isEdited(), chat.updatedAt.toString(), chat.content)
  }

  fun createServerChat(
    request: CreateChatRequest,
    userId: String,
  ): ChatResponse {
    val chat =
      serverChatRepository.save(
        Chat(
          userId = userId,
          contextId = request.contextId,
          content = request.content,
        ),
      )
    return ChatResponse(chat.id!!, userId, MessageType.CHAT, chat.isEdited(), chat.updatedAt.toString(), chat.content)
  }

  fun getRoomChats(
    chatRoomId: String,
    userId: String,
    page: Int,
  ): List<Chat> {
    verifyChatRoomAccess(chatRoomId, userId)

    val chats: List<Chat> = roomChatRepository.findAllByContextId(chatRoomId)
    logger.info("chats : $chats")
    return chats
  }

  fun getServerChats(
    serverId: String,
    userId: String,
    page: Int,
    servers: List<ServerSimpleInfo>,
  ): List<Chat> {
    verifyServerAccess(servers, serverId)

    val chats: List<Chat> = serverChatRepository.findAllByContextId(serverId)

    return chats
  }

  fun updateRoomChat(
    request: UpdateChatRequest,
    userId: String,
  ): ChatResponse {
    val chat = roomChatRepository.findById(request.id).orElseThrow { GlobalException(ErrorCode.CHAT_NOT_FOUND) }
    verifyIfAuthor(userId, chat)
    chat.updateContent(request.content)
    roomChatRepository.save(chat)
    return ChatResponse(chat.id!!, userId, MessageType.UPDATE, chat.isEdited(), chat.updatedAt.toString(), chat.content)
  }

  fun updateServerChat(
    request: UpdateChatRequest,
    userId: String,
  ): ChatResponse {
    val chat = serverChatRepository.findById(request.id).orElseThrow { GlobalException(ErrorCode.CHAT_NOT_FOUND) }
    verifyIfAuthor(userId, chat)
    chat.updateContent(request.content)
    serverChatRepository.save(chat)
    return ChatResponse(chat.id!!, userId, MessageType.UPDATE, chat.isEdited(), chat.updatedAt.toString(), chat.content)
  }

  fun deleteRoomChat(
    chatId: String,
    userId: String,
  ): ChatResponse {
    val chat = roomChatRepository.findById(chatId).orElseThrow { GlobalException(ErrorCode.CHAT_NOT_FOUND) }
    verifyIfAuthor(userId, chat)
    roomChatRepository.delete(chat)
    return ChatResponse(chat.id!!, userId, MessageType.DELETE, chat.isEdited(), chat.updatedAt.toString(), chat.content)
  }

  fun deleteServerChat(
    chatId: String,
    userId: String,
  ): ChatResponse {
    val chat = serverChatRepository.findById(chatId).orElseThrow { GlobalException(ErrorCode.CHAT_NOT_FOUND) }
    verifyIfAuthor(userId, chat)
    serverChatRepository.delete(chat)
    return ChatResponse(chat.id!!, userId, MessageType.DELETE, chat.isEdited(), chat.updatedAt.toString(), chat.content)
  }

  private fun verifyIfAuthor(
    userId: String,
    chat: Chat,
  ) {
    if (userId != chat.userId) {
      throw GlobalException(ErrorCode.FORBIDDEN_CHAT)
    }
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
        ChatResponse(
          id = chat.id!!,
          sender = chat.userId,
          messageType = MessageType.CHAT,
          isEdited = chat.isEdited(),
          sentAt = chat.createdAt.toString(),
          content = chat.content,
        )
      }
    return chatResponse
  }
}
