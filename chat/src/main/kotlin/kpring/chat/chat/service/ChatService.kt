package kpring.chat.chat.service

import kpring.chat.chat.model.RoomChat
import kpring.chat.chat.model.ServerChat
import kpring.chat.chat.repository.RoomChatRepository
import kpring.chat.chat.repository.ServerChatRepository
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.request.CreateChatRequest
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
  fun createChat(
    request: CreateChatRequest,
    userId: String,
  ) {
    val roomChat =
      roomChatRepository.save(
        RoomChat(
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
  ): List<ChatResponse> {
    verifyChatRoomAccess(chatRoomId, userId)

    val pageable: Pageable = PageRequest.of(page, pageSize)
    val roomChats: List<RoomChat> = roomChatRepository.findAllByRoomId(chatRoomId, pageable)

    return convertRoomChatsToResponses(roomChats)
  }

  fun getServerChats(
    serverId: String,
    userId: String,
    page: Int,
    servers: List<ServerSimpleInfo>,
  ): List<ChatResponse> {
    verifyServerAccess(servers, serverId)

    val pageable: Pageable = PageRequest.of(page, pageSize)
    val chats: List<ServerChat> = serverChatRepository.findAllByServerId(serverId, pageable)

    return convertServerChatsToResponses(chats)
  }

  fun verifyServerAccess(
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

  fun convertRoomChatsToResponses(roomChats: List<RoomChat>): List<ChatResponse> {
    val chatResponse =
      roomChats.map { chat ->
        ChatResponse(chat.id!!, chat.isEdited(), chat.createdAt.toString(), chat.content)
      }
    return chatResponse
  }

  fun convertServerChatsToResponses(chats: List<ServerChat>): List<ChatResponse> {
    val chatResponse =
      chats.map { chat ->
        ChatResponse(chat.id!!, chat.isEdited(), chat.createdAt.toString(), chat.content)
      }
    return chatResponse
  }
}
