package kpring.core.chat.chat.dto.request

import jakarta.validation.constraints.NotNull

data class UpdateChatRequest(
  @field:NotNull
  val id: String,
  @field:NotNull
  val type: ChatType,
  val content: String,
)
