package kpring.chat.chat.service

import kpring.chat.chat.api.v1.WebSocketChatController
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatCustomRepository
import kpring.chat.chat.repository.ChatRepository
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateChatRequest
import kpring.core.chat.chat.dto.request.UpdateChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import kpring.core.chat.model.ChatType
import kpring.core.chat.model.MessageType
import kpring.core.server.dto.ServerSimpleInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ChatService(
  private val chatRepository: ChatRepository,
  private val chatRoomRepository: ChatRoomRepository,
  private val chatCustomRepository: ChatCustomRepository,
  @Value("\${page.size}") val pageSize: Int = 100,
) {
  private val logger: Logger = LoggerFactory.getLogger(WebSocketChatController::class.java)

  fun createRoomChat(
    request: CreateChatRequest,
    userId: String,
  ): ChatResponse {
    verifyChatRoomAccess(request.contextId, userId)
    val chat =
      chatRepository.save(
        Chat(
          userId = userId,
          type = request.type,
          contextId = request.contextId,
          content = request.content,
        ),
      )
    return ChatResponse(chat.id!!, userId, MessageType.CHAT, chat.isEdited(), chat.updatedAt.toString(), chat.content)
  }

  fun createServerChat(
    request: CreateChatRequest,
    userId: String,
    servers: List<ServerSimpleInfo>,
  ): ChatResponse {
    verifyServerAccess(servers, request.contextId)
    val chat =
      chatRepository.save(
        Chat(
          userId = userId,
          type = request.type,
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
    size: Int,
  ): List<Chat> {
    verifyChatRoomAccess(chatRoomId, userId)
    val chats: List<Chat> = chatCustomRepository.findListByContextIdWithPaging(chatRoomId, page, size, ChatType.ROOM)
    logger.info("chats : $chats")
    return chats
  }

  fun getServerChats(
    serverId: String,
    userId: String,
    page: Int,
    size: Int,
    servers: List<ServerSimpleInfo>,
  ): List<Chat> {
    verifyServerAccess(servers, serverId)
    val chats: List<Chat> = chatCustomRepository.findListByContextIdWithPaging(serverId, page, size, ChatType.SERVER)
    return chats
  }

  fun updateChat(
    request: UpdateChatRequest,
    userId: String,
  ): ChatResponse {
    val chat = chatRepository.findById(request.id).orElseThrow { GlobalException(ErrorCode.CHAT_NOT_FOUND) }
    verifyIfAuthor(userId, chat)
    chat.updateContent(request.content)
    chatRepository.save(chat)
    return ChatResponse(chat.id!!, userId, MessageType.UPDATE, chat.isEdited(), chat.updatedAt.toString(), chat.content)
  }

  fun deleteChat(
    chatId: String,
    userId: String,
  ): ChatResponse {
    val chat = chatRepository.findById(chatId).orElseThrow { GlobalException(ErrorCode.CHAT_NOT_FOUND) }
    verifyIfAuthor(userId, chat)
    chatRepository.delete(chat)
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
