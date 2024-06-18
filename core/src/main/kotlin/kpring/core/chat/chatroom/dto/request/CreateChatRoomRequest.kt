package kpring.core.chat.chatroom.dto.request

import jakarta.validation.constraints.NotNull

data class CreateChatRoomRequest(
  @field:NotNull
  val users: List<String>,
)
