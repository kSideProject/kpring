package kpring.chat.chat.service

import kpring.chat.chat.model.Chat
import kpring.chat.chat.model.ServerChat
import kpring.chat.chat.repository.RoomChatRepository
import kpring.chat.chat.repository.ServerChatRepository
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateRoomChatRequest
import kpring.core.chat.chat.dto.request.CreateServerChatRequest
import kpring.core.chat.chat.dto.response.ChatResponse
import kpring.core.global.dto.response.ApiResponse
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
  /*
     business logic
   */
  fun createRoomChat(
    request: CreateRoomChatRequest,
    userId: String,
  ) {
    val chat =
      roomChatRepository.save(
        Chat(
          userId = userId,
          roomId = request.room,
          content = request.content,
        ),
      )
  }

  fun getRoomChats(
    chatRoomId: String,
    userId: String,
    page: Int,
  ): ApiResponse<List<ChatResponse>> {
    verifyChatRoomAccess(chatRoomId, userId)

    val pageable: Pageable = PageRequest.of(page, pageSize)
    val chats: List<Chat> = roomChatRepository.findAllByRoomId(chatRoomId, pageable)

    return ApiResponse(data = convertRoomChatsToResponses(chats))
  }

  fun createServerChat(
    request: CreateServerChatRequest,
    userId: String,
  ) {
    val chat =
      serverChatRepository.save(
        ServerChat(
          userId = userId,
          serverId = request.server,
          content = request.content,
        ),
      )
  }

  fun getServerChats(
    serverId: String,
    userId: String,
    page: Int,
    servers: List<ServerSimpleInfo>,
  ): ApiResponse<List<ChatResponse>> {
    verifyUserHasJoinedServer(servers, serverId)

    val pageable: Pageable = PageRequest.of(page, pageSize)
    val chats: List<ServerChat> = serverChatRepository.findAllByServerId(serverId, pageable)

    return ApiResponse(data = convertServerChatsToResponses(chats))
  }

  fun verifyUserHasJoinedServer(
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

  fun verifyChatRoomAccess(
    chatRoomId: String,
    userId: String,
  ) {
    if (!chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId)) {
      throw GlobalException(ErrorCode.FORBIDDEN_CHATROOM)
    }
  }

  fun convertRoomChatsToResponses(chats: List<Chat>): List<ChatResponse> {
    val chatResponse =
      chats.map { chat ->
        ChatResponse(chat.id!!, chat.isEdited(), chat.createdAt, chat.content)
      }
    return chatResponse
  }

  fun convertServerChatsToResponses(chats: List<ServerChat>): List<ChatResponse> {
    val chatResponse =
      chats.map { chat ->
        ChatResponse(chat.id!!, chat.isEdited(), chat.createdAt, chat.content)
      }
    return chatResponse
  }
}
