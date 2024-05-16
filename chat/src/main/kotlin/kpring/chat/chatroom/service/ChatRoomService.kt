package kpring.chat.chatroom.service

import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
  private val chatRoomRepository: ChatRoomRepository,
) {
  fun createChatRoom(
    request: CreateChatRoomRequest,
    userId: String,
  ) {
    val chatRoom = ChatRoom()
    chatRoom.addUsers(request.users)
    chatRoomRepository.save(chatRoom)
  }

  fun exitChatRoom(
    chatRoomId: String,
    userId: String,
  ) {
    verifyAuthorizationForChatRoom(chatRoomId, userId)
    val chatRoom: ChatRoom = getChatRoom(chatRoomId)
    chatRoom.removeUser(userId)
    chatRoomRepository.save(chatRoom)
  }

  fun inviteToChatRoomByUserId(
    userId: String,
    inviterId: String,
    chatRoomId: String,
  ) {
    verifyAuthorizationForChatRoom(chatRoomId, inviterId)
    val chatRoom: ChatRoom = getChatRoom(chatRoomId)
    chatRoom.addUser(userId)
    chatRoomRepository.save(chatRoom)
  }

  fun verifyAuthorizationForChatRoom(
    chatRoomId: String,
    userId: String,
  ) {
    // check if there is a chatroom with the chatRoomId and the user is one of the members
    if (!chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId)) {
      throw GlobalException(ErrorCode.UNAUTHORIZED_CHATROOM)
    }
  }

  fun getChatRoom(chatRoomId: String): ChatRoom {
    val chatRoom: ChatRoom =
      chatRoomRepository.findById(chatRoomId).orElseThrow { GlobalException(ErrorCode.CHATROOM_NOT_FOUND) }
    return chatRoom
  }
}
