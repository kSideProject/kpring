package kpring.core.chat.chat.dto.request

import jakarta.validation.constraints.NotNull
import kpring.core.chat.model.ChatType

data class UpdateChatRequest(
  @field:NotNull
  val id: String,
  @field:NotNull
  val type: ChatType,
  val content: String,
)
