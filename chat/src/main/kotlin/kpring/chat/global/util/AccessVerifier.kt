package kpring.chat.global.util

import kpring.chat.chatroom.repository.ChatRoomRepository
import kpring.chat.global.exception.ErrorCode
import kpring.chat.global.exception.GlobalException
import kpring.core.server.dto.ServerSimpleInfo
import org.springframework.stereotype.Component

@Component
class AccessVerifier(
  private val chatRoomRepository: ChatRoomRepository,
) {
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
}
