package kpring.core.chat.chat.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateRoomChatRequest(
  @field:NotNull
  val room: String,
  @field:NotBlank
  val content: String,
)
