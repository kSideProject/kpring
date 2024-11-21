package kpring.core.chat.chatroom.dto.request

import jakarta.validation.constraints.NotNull

data class ExpelChatRoomRequest(
  @field:NotNull
  val expelUserId: String,
  @field:NotNull
  val chatRoomId: String,
)
