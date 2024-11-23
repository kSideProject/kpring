package kpring.core.chat.chatroom.dto.request

import jakarta.validation.constraints.NotNull

data class TransferChatRoomOwnerRequest(
  @field:NotNull
  val newOwnerId: String,
  @field:NotNull
  val chatRoomId: String,
)
