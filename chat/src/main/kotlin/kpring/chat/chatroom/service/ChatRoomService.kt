package kpring.chat.chatroom.service

import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import kpring.chat.chatroom.dto.ChatWrapper
import kpring.chat.chatroom.dto.InvitationInfo
import kpring.chat.chatroom.model.ChatRoom
import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.chat.global.util.AccessVerifier
import kpring.core.chat.chat.dto.response.ChatResponse
import kpring.core.chat.chat.dto.response.EventType
import kpring.core.chat.chat.dto.response.InvitationResponse
import kpring.core.chat.chatroom.dto.request.CreateChatRoomRequest
import kpring.core.chat.chatroom.dto.request.ExpelChatRoomRequest
import kpring.core.chat.chatroom.dto.request.TransferChatRoomOwnerRequest
import kpring.core.chat.model.ChatType
import kpring.core.chat.model.MessageType
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
  private val chatRoomRepository: ChatRoomRepository,
  private val chatRepository: ChatRepository,
  private val invitationService: InvitationService,
  private val accessVerifier: AccessVerifier,
) {
  fun createChatRoom(
    request: CreateChatRoomRequest,
    userId: String,
  ): ChatWrapper {
    val chatRoom = ChatRoom(ownerId = userId, members = mutableSetOf(userId))
    chatRoom.addUsers(request.users)
    val saved = chatRoomRepository.save(chatRoom)
    return createChatRoomMessage(saved.id!!, "방이 생성되었습니다.", EventType.SYSTEM)
  }

  fun exitChatRoom(
    chatRoomId: String,
    userId: String,
  ): ChatWrapper {
    accessVerifier.verifyChatRoomAccess(chatRoomId, userId)
    val chatRoom: ChatRoom = getChatRoom(chatRoomId)
    chatRoom.removeUser(userId)
    chatRoomRepository.save(chatRoom)
    return createChatRoomMessage(chatRoom.id!!, "${userId}님이 방에서 나갔습니다.", EventType.SYSTEM) // TODO : 닉네임으로 변경
  }

  fun getChatRoomInvitation(
    chatRoomId: String,
    userId: String,
  ): InvitationResponse {
    accessVerifier.verifyChatRoomAccess(chatRoomId, userId)
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
  ): ChatWrapper {
    val invitationInfo = invitationService.getInvitationInfoFromCode(code)
    verifyInvitationExistence(invitationInfo)
    val chatRoom = getChatRoom(invitationInfo.chatRoomId)
    chatRoom.addUser(userId)
    chatRoomRepository.save(chatRoom)
    return createChatRoomMessage(chatRoom.id!!, "${userId}님이 방에 들어왔습니다.", EventType.SYSTEM) // TODO : 닉네임으로 변경
  }

  fun expelFromChatRoom(
    expelChatRoomRequest: ExpelChatRoomRequest,
    userId: String,
  ): ChatWrapper {
    val chatRoom = getChatRoom(expelChatRoomRequest.chatRoomId)
    accessVerifier.verifyChatRoomOwner(expelChatRoomRequest.chatRoomId, userId)
    chatRoom.removeUser(expelChatRoomRequest.expelUserId)
    chatRoomRepository.save(chatRoom)
    return createChatRoomMessage(chatRoom.id!!, "${expelChatRoomRequest.expelUserId}님이 방에서 내보내졌습니다.", EventType.SYSTEM) // TODO : 닉네임으로 변경
  }

  fun transferChatRoomOwnerShip(
    transferChatRoomOwnerRequest: TransferChatRoomOwnerRequest,
    userId: String,
  ): ChatWrapper {
    val chatRoom = getChatRoom(transferChatRoomOwnerRequest.chatRoomId)
    accessVerifier.verifyChatRoomOwner(transferChatRoomOwnerRequest.chatRoomId, userId)
    accessVerifier.verifyChatRoomOwner(transferChatRoomOwnerRequest.chatRoomId, transferChatRoomOwnerRequest.newOwnerId)
    chatRoom.transferOwnership(transferChatRoomOwnerRequest.newOwnerId)
    chatRoomRepository.save(chatRoom)
    return createChatRoomMessage(chatRoom.id!!, "${transferChatRoomOwnerRequest.newOwnerId}님이 새 방장으로 임명되었습니다.", EventType.CHAT)
  }

  private fun verifyInvitationExistence(invitationInfo: InvitationInfo) {
    if (invitationInfo.code != invitationService.getInvitation(invitationInfo.userId, invitationInfo.chatRoomId)) {
      throw GlobalException(ErrorCode.EXPIRED_INVITATION)
    }
  }

  fun createChatRoomMessage(
    chatRoomId: String,
    content: String,
    eventType: EventType,
  ): ChatWrapper {
    val chat =
      chatRepository.save(
        Chat(
          userId = "",
          chatType = ChatType.ROOM,
          eventType = eventType,
          contextId = chatRoomId,
          content = content,
        ),
      )
    return ChatWrapper(
      chatRoomId,
      ChatResponse(
        id = chat.id!!,
        sender = chat.userId,
        messageType = MessageType.CHAT,
        isEdited = chat.isEdited(),
        sentAt = chat.updatedAt.toString(),
        content = chat.content,
        eventType = chat.eventType,
      ),
    )
  }

  private fun getChatRoom(chatRoomId: String): ChatRoom {
    val chatRoom: ChatRoom =
      chatRoomRepository.findById(chatRoomId).orElseThrow { GlobalException(ErrorCode.CHATROOM_NOT_FOUND) }
    return chatRoom
  }
}
