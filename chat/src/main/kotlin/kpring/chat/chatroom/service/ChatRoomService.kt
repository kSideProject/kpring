package kpring.chat.chatroom.service

import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomService(
  private val chatRoomRepository: ChatRoomRepository,
  private val lockService: DistributedLockService,
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
    verifyChatRoomAccess(chatRoomId, userId)
    val chatRoom: ChatRoom = getChatRoom(chatRoomId)
    chatRoom.removeUser(userId)
    chatRoomRepository.save(chatRoom)
  }

  @Transactional
  fun inviteToChatRoomByUserIdWithLock(
    userId: String,
    inviterId: String,
    chatRoomId: String,
  ): Boolean {
    val lock = lockService.getLock(chatRoomId)

    inviteToChatRoomByUserId(userId, inviterId, chatRoomId)

    lockService.releaseLock(lock.lockId, lock.owner)
    return true
  }

  fun inviteToChatRoomByUserId(
    userId: String,
    inviterId: String,
    chatRoomId: String,
  ) {
    verifyChatRoomAccess(chatRoomId, inviterId)
    val chatRoom: ChatRoom = getChatRoom(chatRoomId)
    chatRoom.addUser(userId)
    chatRoomRepository.save(chatRoom)
  }

  fun verifyChatRoomAccess(
    chatRoomId: String,
    userId: String,
  ) {
    if (!chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId)) {
      throw GlobalException(ErrorCode.FORBIDDEN_CHATROOM)
    }
  }

  fun getChatRoom(chatRoomId: String): ChatRoom {
    val chatRoom: ChatRoom =
      chatRoomRepository.findById(chatRoomId).orElseThrow { GlobalException(ErrorCode.CHATROOM_NOT_FOUND) }
    return chatRoom
  }
}
