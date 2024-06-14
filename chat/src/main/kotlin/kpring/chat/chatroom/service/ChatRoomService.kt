package kpring.chat.chatroom.service

import kpring.chat.chatroom.dto.InvitationInfo
import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.chat.chat.dto.response.InvitationResponse
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
  private val chatRoomRepository: ChatRoomRepository,
  private val invitationService: InvitationService,
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

  fun getChatRoomInvitation(
    chatRoomId: String,
    userId: String,
  ): InvitationResponse {
    verifyChatRoomAccess(chatRoomId, userId)
    var code = invitationService.getInvitation(userId, chatRoomId)
    if (code == null) {
      code = invitationService.setInvitation(userId, chatRoomId)
    }
    val encodedCode = invitationService.generateKeyAndCode(userId, chatRoomId, code)
    return InvitationResponse(encodedCode)
  }

  fun joinChatRoom(
    code: String,
    userId: String,
  ): Boolean {
    val decodedCode = invitationService.decodeCode(code)
    val invitationInfo = invitationService.getInvitationInfoFromCode(decodedCode)
    verifyInvitationExistence(invitationInfo)
    val chatRoom = getChatRoom(invitationInfo.chatRoomId)
    chatRoom.addUser(userId)
    chatRoomRepository.save(chatRoom)
    return true
  }

  private fun verifyInvitationExistence(invitationInfo: InvitationInfo) {
    if (invitationInfo.code != invitationService.getInvitation(invitationInfo.userId, invitationInfo.chatRoomId)) {
      throw GlobalException(ErrorCode.EXPIRED_INVITATION)
    }
  }

  private fun verifyChatRoomAccess(
    chatRoomId: String,
    userId: String,
  ) {
    if (!chatRoomRepository.existsByIdAndMembersContaining(chatRoomId, userId)) {
      throw GlobalException(ErrorCode.FORBIDDEN_CHATROOM)
    }
  }

  private fun getChatRoom(chatRoomId: String): ChatRoom {
    val chatRoom: ChatRoom =
      chatRoomRepository.findById(chatRoomId).orElseThrow { GlobalException(ErrorCode.CHATROOM_NOT_FOUND) }
    return chatRoom
  }
}
